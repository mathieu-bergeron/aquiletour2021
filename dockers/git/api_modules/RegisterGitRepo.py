from fastapi import Response, status
from fastapi.responses import JSONResponse
import mysql.connector
import sqlite3
import json
import re
import utils.normalize_data
import utils.task_utils

#  {
#      "_C": "RegisterGitRepo",
#      "courseId": "IntroProg",
#      "semesterId": "H2021",
#      "teacherId" : "nicolas.leduc"
#      "groupId": "01",
#      "studentId": "bob.berancourt",
#      "repoPath": "/Semaine 01",
#      "repoUrl": "https://github.com/test/test.git"
#  }
def process(api_req, maria_conn, lite_conn):
    if not 'groupId' in api_req:
        api_req['groupId'] = '__NONE__'
    if not 'repoPath' in api_req:
        api_req['repoPath'] = '/'
    if maria_conn and lite_conn:
        try:
            host = 'ZZ'
            if re.search('gitlab', api_req['repoUrl']):
                host = 'GL'
            elif re.search('github', api_req['repoUrl']):
                host = 'GH'
            elif re.search('azure', api_req['repoUrl']):
                host = 'AZ'
            semester = utils.normalize_data.normalize_semesterId(api_req['semesterId'])
            course = utils.normalize_data.normalize_courseId(api_req['courseId'])
            teacher = utils.normalize_data.normalize_teacherId(api_req['teacherId'])
            group = utils.normalize_data.normalize_groupId(api_req['groupId'])
            student = utils.normalize_data.normalize_studentId(api_req['studentId'])
            maria_cur = maria_conn.cursor()
            maria_cur.execute('''INSERT INTO repository 
                VALUES (%s,%s,%s,%s,%s,%s,%s,%s)''',
                (api_req['repoUrl'], host, semester, course, teacher, group, student, api_req['repoPath']))
            maria_conn.commit()
            body = utils.task_utils.add_task({'_C':'HookTask', 'depot':api_req['repoUrl']}, 5, lite_conn)
            response = JSONResponse(content = body)
            response.status_code = status.HTTP_200_OK
        except mysql.connector.errors.IntegrityError:
            maria_conn.rollback()
            print('Duplicate depot or invalid data')
            response = Response()
            response.status_code = status.HTTP_304_NOT_MODIFIED
        except mysql.connector.errors.DataError:
            print('Data format error')
            response = JSONResponse()
            response.status_code = status.HTTP_400_BAD_REQUEST
        except KeyError:
            maria_conn.rollback()
            response = JSONResponse()
            response.status_code = status.HTTP_400_BAD_REQUEST
    else:
        response = JSONResponse()
        response.status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
    return response
