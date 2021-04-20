from fastapi import Response, status
from fastapi.responses import JSONResponse
import mysql.connector
import sqlite3
import json
import re
import utils.normalize_data

# {
#       "_C": "GetCommitsForPath",
#       "courseId": "mathieu.bergeron/StruDon",
#       "semesterId": "H2021",
#       "groupId": "01",
#       "studentId": "1234500",
#       "exercisePath": "/Étape 1"
#   }
  
def process(api_req, maria_conn, lite_conn):
#    if not 'groupId' in api_req:
#        api_req['groupId'] = None
    if not 'repoPath' in api_req:
        api_req['repoPath'] = '/'
    if maria_conn:
        try:
            maria_cursor = maria_conn.cursor()
            maria_cursor.execute('''SELECT commit.* 
            FROM commit
            LEFT JOIN repository 
                ON commit.repo_url = repository.repo_url
            LEFT JOIN exercise
                ON repository.repo_path = exercise.repo_path
            WHERE exercise.session_id = %s AND exercise.course_id = %s AND exercise.group_id = %s AND exercise.exercise_path = %s''',
                (
                utils.normalize_data.normalize_session(api_req['semesterId']),
                utils.normalize_data.normalize_courseId(api_req['courseId']),
                utils.normalize_data.normalize_group(api_req['groupId']),
                api_req['repoPath']))

            row = maria_cursor.fetch()
            print('an expedition')
            while row is not None:
                print(row)
                row = maria.fetchone()

            data = {
                'name': 'Vitor',
                'location': 'Finland',
                'is_active': True,
                'count': 28
            }   
            response = JSONResponse(data)
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
