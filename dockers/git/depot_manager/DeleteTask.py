import os
from datetime import datetime
import sqlite3
import mysql.connector
import shutil
import json
import git
import utils.normalize_data
import utils.task_utils
import utils.data_matcher
import utils.net_utils

#  {
#      "_C": "DeleteTask",
#      "depot": "https://github.com/test/test.git",
#  }
def process(task_req, maria_conn, lite_conn):
    try:
        depot = task_req['depot']
        maria_cur = maria_conn.cursor(dictionary = True)
        maria_cur.execute('SELECT * FROM repository WHERE repo_url=%s',(depot,))
        record = maria_cur.fetchone()
        print(record)
        if record:
            depotDir = record['repo_url'].split('/')
            depotDir = depotDir[len(depotDir)-1].replace('.git','') + '-' + record['repo_host']
            depotPath = os.path.join('depot',record['session_id'],record['course_id'],record['teacher_id'],record['student_id'],depotDir)
            trashPath = os.path.join('depot','trash',record['session_id'],record['course_id'],record['teacher_id'],record['student_id'],datetime.now().strftime('%y%m%d-%H%M%S'),depotDir)
            print(depotPath)
            if os.path.isdir(depotPath) :
                shutil.move(depotPath,trashPath)
            maria_cur.execute('DELETE FROM commit_file WHERE repo_url = %s;',(depot,))
            maria_cur.execute('DELETE FROM commit WHERE repo_url = %s;',(depot,))
            maria_cur.execute('DELETE FROM repository WHERE repo_url = %s;',(depot,))
            maria_conn.commit()
            answer = 'DONE'
    except mysql.connector.errors.DataError:
        maria_conn.rollback()
        answer = 'Data format error'
    except KeyError:
        maria_conn.rollback()
        answer = 'BAD REQUEST'
    return (True, json.dumps({'status': answer}))
