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
                api_req['exercisePath']))

            # print(utils.normalize_data.normalize_session(api_req['semesterId']))
            # print(utils.normalize_data.normalize_courseId(api_req['courseId']))
            # print(utils.normalize_data.normalize_group(api_req['groupId']))
            # print(api_req['exercisePath'])
            
            commitListModel= {}
            commitListModel['_C'] = 'CommitListModel'
            commitListModel['courseId'] = utils.normalize_data.normalize_session(api_req['semesterId'])
            commitListModel['semesterId'] = utils.normalize_data.normalize_courseId(api_req['courseId'])
            commitListModel['groupId'] = utils.normalize_data.normalize_group(api_req['groupId'])
            commitListModel['studentId'] = api_req['exercisePath']
            commits = {}
            commits['_C'] = 'ObservableCommitList'
            value = []

            commitRows = maria_cursor.fetchall()
            print(commitRows)
            for commitRow in commitRows:
                commitData = {}
                commitData['_C'] = 'Commit'
                commitData['commitId'] = commitRow[1]
                commitData['exercisePathIfCompleted'] = commitRow[5]
                commitData['commitMessageFirstLine'] = commitRow[3] #TODO only show the ? first characters
                commitData['commitMessage'] = commitRow[3]


                maria_cursor.execute('''SELECT unix_timestamp(commit_date) 
                FROM commit 
                WHERE commit_id = %s AND repo_url = %s ''',
                (
                commitRow[1],
                commitRow[0]
                ))
                timeStampEpoch = maria_cursor.fetchone()
                commitData['timeStamp'] = str(timeStampEpoch[0])

                maria_cursor.execute('''SELECT commit_file.* 
                FROM commit_file
                WHERE commit_file.repo_url = %s AND commit_file.commit_id = %s''',
                (
                commitRow[0],
                commitRow[1]
                ))

                modifiedFiles = []
                commitFilesRows = maria_cursor.fetchall()
                for commitFilesRow in commitFilesRows:
                    commitFileData = {}
                    commitFileData['path'] = commitFilesRow[2]
                    commitFileData['estimatedEffort'] = commitFilesRow[5]
                    commitFileData['exercisePath'] = commitFilesRow[6]
                    commitFileData['message'] = "no field in database for now"
                    modifiedFiles.append(commitFileData)

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
