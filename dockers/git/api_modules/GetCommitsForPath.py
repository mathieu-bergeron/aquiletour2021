from fastapi import Response, status
from fastapi.responses import JSONResponse
import mysql.connector
import sqlite3
import json
import re
import utils.normalize_data
import time
import calendar

# {
#       "_C": "GetCommitsForPath",
#       "courseId": "mathieu.bergeron/StruDon",
#       "semesterId": "H2021",
#       "groupId": "01",
#       "studentId": "1234500",
#       "exercisePath": "/TP1/Exercice 1"
#   }
  
def process(api_req, maria_conn, lite_conn):
#    if not 'groupId' in api_req:
#        api_req['groupId'] = None
    if not 'repoPath' in api_req:
        api_req['repoPath'] = '/'
    if maria_conn:
        try:
            maria_cursor = maria_conn.cursor()

            maria_cursor = getCommitInfo(maria_cursor, api_req)

            commitRows = maria_cursor.fetchall()
            # print(utils.normalize_data.normalize_session(api_req['semesterId']))
            # print(utils.normalize_data.normalize_courseId(api_req['courseId']))
            # print(utils.normalize_data.normalize_group(api_req['groupId']))
            # print(api_req['exercisePath'])
            
            commitListModel= {}
            commitListModel['_C'] = 'CommitListModel'
            commitListModel['courseId'] = utils.normalize_data.normalize_session(api_req['semesterId'])
            commitListModel['semesterId'] = utils.normalize_data.normalize_courseId(api_req['courseId'])
            commitListModel['groupId'] = utils.normalize_data.normalize_group(api_req['groupId'])
            commitListModel['exercisePath'] = api_req['exercisePath']
            commitListModel['studentId'] = utils.normalize_data.normalize_studentId(api_req['studentId'])

            print(commitRows)
            commits = {}
            commits['_C'] = 'ObservableCommitList'
            value = []

            commitIndex = 0
            stopSearch = False
            while commitIndex < len(commitRows):
                commitRow = commitRows[commitIndex]
                commitData = {}
                modifiedFiles = []
                estimatedEffortAverage = 0
                effortIndex = 0

                commitData['_C'] = 'Commit'
                commitData['commitId'] = commitRow[1]
                commitData['exercisePathIfCompleted'] = commitRow[5]
                commitData['commitMessageFirstLine'] = commitRow[3]
                commitData['commitMessage'] = commitRow[4]
                commitData['timeStamp'] = str(commitRow[6]) # commit info
                print(commitRows[commitIndex])
                print(commitIndex)
                commitId = commitRows[commitIndex][1]
                while commitRow[1] == commitId: 
                    commitFileData = {}
                    commitFileData['_C'] = 'CommitFile'
                    commitFileData['path'] = commitRow[9]
                    commitFileData['estimatedEffort'] = commitRow[12]
                    estimatedEffortAverage += commitRow[12]
                    effortIndex += 1
                    commitFileData['exercisePath'] = commitRow[13]
                    commitFileData['message'] = "no field in database for now"
                    modifiedFiles.append(commitFileData)
                    commitIndex += 1
                    if commitIndex >= len(commitRows):
                        commitId = ''  # null so the while finishes
                    else:
                        print(commitIndex)
                        commitId = commitRows[commitIndex][1]

                commitData['estimatedEffort'] = estimatedEffortAverage = estimatedEffortAverage / effortIndex 
                commitData['modifiedFiles'] = modifiedFiles
                value.append(commitData)

            commits['value'] = value
            commitListModel['commits'] = commits

            response = JSONResponse(commitListModel)
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
    if api_req['courseId'] == '*':
        maria_cursor.execute('''SELECT commit.*, unix_timestamp(commit.commit_date), commit_file.*
        FROM commit
        LEFT JOIN repository 
            ON commit.repo_url = repository.repo_url
        LEFT JOIN commit_file
            ON commit.commit_id = commit_file.commit_id AND commit.repo_url = commit_file.repo_url
        WHERE repository.session_id = %s AND commit_file.exercise_path = %s AND repository.student = %s
        ORDER BY commit.commit_id''',
        (
        utils.normalize_data.normalize_session(api_req['semesterId']),
        api_req['exercisePath'],
        utils.normalize_data.normalize_studentId(api_req['studentId'])) 
        )
    else:
        maria_cursor.execute('''SELECT commit.*, unix_timestamp(commit.commit_date), commit_file.*
        FROM commit
        LEFT JOIN repository 
            ON commit.repo_url = repository.repo_url
        LEFT JOIN commit_file
            ON commit.commit_id = commit_file.commit_id AND commit.repo_url = commit_file.repo_url
        WHERE repository.session_id = %s AND repository.course_id = %s AND repository.group_id = %s AND commit_file.exercise_path = %s AND repository.student = %s
        ORDER BY commit.commit_id''',
        (
        utils.normalize_data.normalize_session(api_req['semesterId']),
        utils.normalize_data.normalize_courseId(api_req['courseId']),
        utils.normalize_data.normalize_group(api_req['groupId']),
        api_req['exercisePath'],
        utils.normalize_data.normalize_studentId(api_req['studentId'])
        ))
    
    return maria_cursor

