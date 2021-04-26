from fastapi import Response, status
from fastapi.responses import JSONResponse
import mysql.connector
import sqlite3
import json
import re
import utils.normalize_data

# {
#       "_C": "GetLateStudents",
#       "courseId": "mathieu.bergeron/StruDon",
#       "semesterId": "H2021",
#       "groupId": "01",
#       "exercisePath": "/TP1/Exercice 1",
#       "deadline": 1615215942
#   }
def process(api_req, maria_conn, lite_conn):

    if maria_conn:
        try:
            maria_cursor = maria_conn.cursor()
            lateStudentModel = {}
            lateStudentModel['_C'] = 'LateStudentsModel'
            lateStudentModel['semesterId'] = api_req['semesterId']
            lateStudentModel['courseId'] = api_req['courseId']
            lateStudentModel['groupId'] = api_req['groupId']
            lateStudentModel['exercisePath'] = api_req['exercisePath']
            lateStudentModel['deadline'] = api_req['deadline']

            maria_cursor.execute('''SELECT repository.student
            FROM repository
            LEFT JOIN commit
                ON repository.repo_url = commit.repo_url
            LEFT JOIN exercise
                ON repository.repo_path = exercise.repo_path
            WHERE exercise.session_id = %s AND exercise.exercise_path = %s AND exercise.course_id = %s
            AND exercise.group_id = %s 
            AND unix_timestamp(commit.commit_date) * 1000 > CAST(%s AS INT) ''',
                (
                utils.normalize_data.normalize_session(api_req['semesterId']),
                api_req['exercisePath'],
                utils.normalize_data.normalize_courseId(api_req['courseId']),
                utils.normalize_data.normalize_group(api_req['groupId']),
                str(api_req['deadline'])
                )
            )
            # print(utils.normalize_data.normalize_session(api_req['semesterId']))
            # print(api_req['exercisePath'])
            # print(utils.normalize_data.normalize_courseId(api_req['courseId']))
            # print(utils.normalize_data.normalize_group(api_req['groupId']))
            # print(str(api_req['deadline']))
            studentIds = []
            studentRows = maria_cursor.fetchall()
            for studentRow in studentRows:
                studentIds.append(studentRows[0][0])
            lateStudentModel['studentIds'] = studentIds
            response = JSONResponse(lateStudentModel)
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
