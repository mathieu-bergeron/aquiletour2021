from fastapi import Response, status
from fastapi.responses import JSONResponse
import mysql.connector
import sqlite3
import json
import re
import utils.normalize_data

# {
#       "_C":"GetStudentSummaries",
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
            studentSummariesModel = {}
            studentSummariesModel['_C'] = 'LateStudentsModel'
            studentSummariesModel['semesterId'] = api_req['semesterId']
            studentSummariesModel['groupId'] = api_req['groupId']
            studentSummariesModel['exercisePath'] = api_req['exercisePath']
            summaries = []

            maria_cursor.execute('''SELECT repository.student
            FROM repository
            LEFT JOIN commit
                ON repository.repo_url = commit.repo_url
            LEFT JOIN exercise
                ON repository.repo_path = exercise.repo_path
            WHERE exercise.session_id = %s AND exercise.exercise_path = %s AND exercise.course_id = %s
            AND exercise.group_id = %s ''',
                (
                utils.normalize_data.normalize_session(api_req['semesterId']),
                api_req['exercisePath'],
                utils.normalize_data.normalize_courseId(api_req['courseId']),
                utils.normalize_data.normalize_group(api_req['groupId'])
                )
            )
            studentRows = maria_cursor.fetchall()
            studentIds = []
            print('banana')
            for studentRow in studentRows:
                if studentRows[0][0] not in studentIds:
                    studentIds.append(studentRows[0][0])

            for studentId in studentIds:
                studentSummary = {}
                studentSummary['_C'] = 'StudentSummary'
                studentSummary['studentId'] = studentId
                print(studentId)

                maria_cursor.execute('''SELECT commit.*
                FROM commit
                LEFT JOIN repository
                    ON repository.repo_url = commit.repo_url
                LEFT JOIN exercise
                    ON repository.repo_path = exercise.repo_path
                WHERE exercise.session_id = %s AND exercise.exercise_path = %s AND exercise.course_id = %s
                AND exercise.group_id = %s AND repository.student = %s''',
                (
                utils.normalize_data.normalize_session(api_req['semesterId']),
                api_req['exercisePath'],
                utils.normalize_data.normalize_courseId(api_req['courseId']),
                utils.normalize_data.normalize_group(api_req['groupId']),
                studentId
                )
                )

                commitRows = maria_cursor.fetchall()
                for commitRow in commitRows:
                    if commitRow[5] == '/':
                        studentSummary['exerciseCompleted'] = False
                        studentSummary['exerciseCompletedBeforeDeadline'] = False
                    else if commitRow[5] == api_req['exercisePath']:
                        #FINAL COMMIT FOUND
                        studentSummary['exerciseCompleted'] = True
                        maria_cursor = getTimeStampEpoch(maria_cursor, commitRow)
                        timeStamp = maria_cursor.fetchall()
                        if timeStamp[0][0] <= api_req['deadline']:
                            studentSummary['exerciseCompletedBeforeDeadline'] = True
                        else:
                            studentSummary['exerciseCompletedBeforeDeadline'] = False
                summaries.append(studentSummary)
            studentSummariesModel['summaries'] = summaries
  
            response = JSONResponse(studentSummariesModel)
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

def getTimeStampEpoch(maria_cursor, commitRow):
    maria_cursor.execute('''SELECT unix_timestamp(commit_date) 
        FROM commit 
        WHERE commit_id = %s AND repo_url = %s ''',
        (
        commitRow[1],
        commitRow[0]
        ))

    return maria_cursor