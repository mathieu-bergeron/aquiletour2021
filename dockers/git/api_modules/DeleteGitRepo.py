from fastapi import Response, status
from fastapi.responses import JSONResponse
import mysql.connector
import sqlite3
import json
import re
import utils.normalize_data

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
#    if not 'groupId' in api_req:
#        api_req['groupId'] = None
    # if not 'repoPath' in api_req:
    #     api_req['repoPath'] = '/'
    if maria_conn:
        try:
            # host = 'ZZ'
            # if re.search('gitlab', api_req['repoUrl']):
            #     host = 'GL'
            # elif re.search('github', api_req['repoUrl']):
            #     host = 'GH'
            # elif re.search('azure', api_req['repoUrl']):
            #     host = 'AZ'
            # DELETE repository, commit, commit_file
            #     FROM repository
            #     INNER JOIN commit_file ON repository.repo_url = commit_file.repo_url
            #     INNER JOIN commit ON repository.repo_url = commit.repo_url
            #     WHERE repo_url = %s
            maria_cur = maria_conn.cursor()
            print( api_req['repoUrl'])
            maria_cur.execute('''
                DELETE FROM commit_file WHERE repo_url = %s;
                ''',
                (api_req['repoUrl'],),
                )
            maria_cur.execute('''
                DELETE FROM commit WHERE repo_url = %s;
                ''',
                (api_req['repoUrl'],),
                )
            maria_cur.execute('''
                DELETE FROM repository WHERE repo_url = %s;
                ''',
                (api_req['repoUrl'],),
                )
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