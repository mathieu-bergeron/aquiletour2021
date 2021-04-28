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
            maria_cursor = getCommitInfo(maria_cursor, api_req)

            studentSummariesModel = {}
            studentSummariesModel['_C'] = 'LateStudentsModel'
            studentSummariesModel['semesterId'] = api_req['semesterId']
            studentSummariesModel['groupId'] = api_req['groupId']
            studentSummariesModel['exercisePath'] = api_req['exercisePath']
            summaries = []
             
            commitRows = maria_cursor.fetchall()
            studentIds = []
            index = 0
            while index < len(commitRows):
                if commitRows[index][0] not in studentIds:
                    studentIds.append(commitRows[index][0])
                index += 1

            print(studentIds)
            index = 0
            for studentId in studentIds:
                finalCommitFound = False
                studentSummary = {}
                studentSummary['_C'] = 'StudentSummary'
                studentSummary['studentId'] = studentId
                oldStudentId = studentId
                lastCommitBeforeDeadline = 0
                lastCommitAfterDeadline = 0
                while studentId == oldStudentId:
                    print(index)
                    oldStudentId = studentId
                    if not finalCommitFound:
                        if commitRows[index][3] == api_req['exercisePath']:
                            #FINAL COMMIT FOUND
                            finalCommitFound = True
                            studentSummary['exerciseCompleted'] = True
                            if commitRows[index][2] <= api_req['deadline']:
                                studentSummary['exerciseCompletedBeforeDeadline'] = True
                            else:
                                studentSummary['exerciseCompletedBeforeDeadline'] = False
                        else:
                            studentSummary['exerciseCompleted'] = False
                            studentSummary['exerciseCompletedBeforeDeadline'] = False

                    if commitRows[index][2] <= api_req['deadline'] and commitRows[index][2] > lastCommitBeforeDeadline:
                        lastCommitBeforeDeadline = commitRows[index][2]
                    elif commitRows[index][2] > api_req['deadline'] and commitRows[index][2] > lastCommitAfterDeadline:
                        lastCommitAfterDeadline = commitRows[index][2]  

                    index += 1
                    if index >= len(commitRows):
                        studentId = ''
                    else:
                        studentId = commitRows[index][0]  #if else to not go out of bound
                    print(studentId)

                studentSummary['lastCommitBeforeDeadline'] = lastCommitBeforeDeadline
                studentSummary['lastCommitAfterDeadline'] = lastCommitAfterDeadline
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

def getCommitInfo(maria_cursor, api_req):
    maria_cursor.execute('''SELECT repository.student, commit.commit_id, unix_timestamp(commit.commit_date) * 1000, commit.exercise_path
            FROM repository
            LEFT JOIN commit
                ON repository.repo_url = commit.repo_url
            LEFT JOIN commit_file
                ON commit.commit_id = commit_file.commit_id AND commit.repo_url = commit_file.repo_url
            WHERE repository.session_id = %s 
            AND commit_file.exercise_path = %s 
            AND repository.course_id = %s
            AND repository.group_id = %s 
            ORDER BY repository.student, commit.commit_id;''',
                (
                utils.normalize_data.normalize_session(api_req['semesterId']),
                api_req['exercisePath'],
                utils.normalize_data.normalize_courseId(api_req['courseId']),
                utils.normalize_data.normalize_group(api_req['groupId'])
                )
            )
    
    return maria_cursor