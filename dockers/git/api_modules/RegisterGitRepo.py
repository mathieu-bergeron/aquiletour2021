from fastapi import Response, status
from fastapi.responses import JSONResponse
import mysql.connector
import sqlite3
import json
import re
import utils.normalize_data

#  {
#      "_C": "RegisterGitRepo",
#      "courseId": "mathieu.bergeron/StruDon",
#      "semesterId": "H2021",
#      "groupId": "01",
#      "studentId": "bob.berancourt",
#      "repoPath": "/TP1",
#      "repoUrl": "https://github.com/test/test.git"
#  }
def process(api_req, maria_conn, lite_conn):
#    if not 'groupId' in api_req:
#        api_req['groupId'] = None
    if not 'repoPath' in api_req:
        api_req['repoPath'] = '/'
    if maria_conn:
        try:
            host = 'ZZ'
            if re.search('gitlab', api_req['repoUrl']):
                host = 'GL'
            elif re.search('github', api_req['repoUrl']):
                host = 'GH'
            elif re.search('azure', api_req['repoUrl']):
                host = 'AZ'
            maria_cur = maria_conn.cursor()
            maria_cur.execute('''INSERT INTO repository 
                VALUES (%s,%s,%s,%s,%s,%s,%s)''',
                (api_req['repoUrl'], host,
                utils.normalize_data.normalize_session(api_req['semesterId']),
                utils.normalize_data.normalize_courseId(api_req['courseId']),
                utils.normalize_data.normalize_group(api_req['groupId']),
                utils.normalize_data.normalize_studentId(api_req['studentId']),
                api_req['repoPath']))
            maria_conn.commit()
            response = JSONResponse()
            response.status_code = status.HTTP_200_OK
        except mysql.connector.errors.IntegrityError:
            print('Duplicate depot or invalid data')
            response = Response()
            response.status_code = status.HTTP_304_NOT_MODIFIED
        except mysql.connector.errors.DataError:
            print('Data format error')
            response = JSONResponse()
            response.status_code = status.HTTP_400_BAD_REQUEST
        except KeyError:
            response = JSONResponse()
            response.status_code = status.HTTP_400_BAD_REQUEST
    else:
        response = JSONResponse()
        response.status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
    return response
