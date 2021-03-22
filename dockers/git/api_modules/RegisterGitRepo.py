from fastapi import Response, status
from fastapi.responses import JSONResponse
import mysql.connector
import sqlite3
import json

#  {
#      "_C":"RegisterGitRepo",
#      "repoUrl":"https://github.com/patate/atelier.git",
#      "semesterId":"H2021",
#      "studentId":"1234567",
#      "courseId":"3C6",
#      "groupId":"01",
#      "exercisePath":"/etape1/atelier",
#  }
def process(api_req, maria_conn, lite_conn):
    if not 'groupId' in api_req:
        api_req['groupId'] = None
    if not 'exercisePath' in api_req:
        api_req['exercisePath'] = None
    if maria_conn:
        try:
            maria_cur = maria_conn.cursor()
            maria_cur.execute('''INSERT INTO depot 
                VALUES (%s,%s,%s,%s,%s,%s)''',
                (api_req['repoUrl'],api_req['semesterId'],api_req['courseId'],api_req['groupId'],api_req['studentId'],api_req['exercisePath']))
            maria_conn.commit()
            response = JSONResponse()
            response.status_code = status.HTTP_200_OK
        except mysql.connector.errors.IntegrityError:
            print('Duplicate depot')
            response = Response()
            response.status_code = status.HTTP_304_NOT_MODIFIED
        except KeyError:
            response = JSONResponse()
            response.status_code = status.HTTP_400_BAD_REQUEST
    return response
