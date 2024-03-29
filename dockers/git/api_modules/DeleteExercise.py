from fastapi import Response, status
from fastapi.responses import JSONResponse
import mysql.connector
import sqlite3
import json
import re
import utils.normalize_data
import utils.task_utils

# {
#       "_C": "DeleteExercise",
#       "courseId": "StruDon",
#       "semesterId": "H2021",
#       "teacherId": "mathieu.bergeron"
#       "groupId": "01",
#       "exercisePath": "/TP1/Exercice 1",
#       "repoPath": "/TP1",
#       "sourceFolderPath": "/exercice01",
#       "completionKeywords": "Exercice 1"
#   }
def process(api_req, maria_conn, lite_conn):
    if not 'groupId' in api_req:
        api_req['groupId'] = '__NONE__'
    if not 'repoPath' in api_req:
        api_req['repoPath'] = '/'
    if maria_conn and lite_conn:
        try:
            semester = utils.normalize_data.normalize_semesterId(api_req['semesterId'])
            course = utils.normalize_data.normalize_courseId(api_req['courseId'])
            teacher = utils.normalize_data.normalize_teacherId(api_req['teacherId'])
            group = utils.normalize_data.normalize_groupId(api_req['groupId'])
            maria_cur = maria_conn.cursor()
            maria_cur.execute('''DELETE FROM exercise 
                WHERE session_id = %s AND course_id = %s AND teacher_id = %s AND exercise_path = %s''',
                (semester, course, teacher, api_req['exercisePath']))
            maria_conn.commit()
            body = utils.task_utils.add_task({'_C':'UpdateTask', 'semesterId':semester, 'courseId':course, 'teacherId':teacher, 'groupId':group}, 9, lite_conn)
            response = JSONResponse(content = body)
            response.status_code = status.HTTP_200_OK
        except mysql.connector.errors.IntegrityError:
            maria_conn.rollback()
            print('Exercise not found or invalid data')
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
