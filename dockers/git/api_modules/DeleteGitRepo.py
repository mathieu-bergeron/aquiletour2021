from fastapi import Response, status
from fastapi.responses import JSONResponse
import mysql.connector
import sqlite3
import json
import re
import utils.normalize_data
import utils.task_utils

#  {
#      "_C": "DeleteGitRepo",
#      "courseId": "nicolas.leduc/IntroProg",
#      "semesterId": "H2021",
#      "groupId": "01",
#      "studentId": "bob.berancourt",
#      "repoPath": "/Semaine 01"
#      "repoUrl": "https://github.com/test/test.git",
#  }
def process(api_req, maria_conn, lite_conn):
    if lite_conn:
        try:
            lite_cur = lite_conn.cursor()
            req = {}
            req['_C'] = 'HookTask'
            req['depot'] = api_req['repoUrl']
            lite_cur.execute('DELETE FROM tasks WHERE start_date is null AND request = ?',
                (json.dumps(req, sort_keys=True),))
            lite_conn.commit()
            req['_C'] = 'DeleteTask'
            body = utils.task_utils.add_task(req, 5, lite_conn)
            response = JSONResponse(content = body)
            response.status_code = status.HTTP_200_OK
        except sqlite3.Error as e:
            print('Database error')
            response = JSONResponse()
            response.status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
        except KeyError:
            lite_conn.rollback()
            response = JSONResponse()
            response.status_code = status.HTTP_400_BAD_REQUEST
    else:
        response = JSONResponse()
        response.status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
    return response
