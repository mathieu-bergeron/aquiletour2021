# from typing import Optional
from fastapi import FastAPI, Request, Response, status
from multiprocessing import Process
import time
import os
import uvicorn
import sqlite3
import mysql.connector
import json
import api

app = FastAPI()
fast_lite_conn = None
fast_maria_conn = None
# @app.get("/")
# def read_root():
#    return {"Hello": "World"}


# @app.get("/items/{item_id}")
# async def read_item(item_id, request: Request, q = None):
#    print(request)
#    print(await request.json())
#    return {"item_id": item_id, "q": q}

@app.post("/_git_api")
async def read_api(request: Request, response: Response):
    global fast_maria_conn
    if fast_maria_conn is None:
        maria_cnf_FD = open('db_conf.json')
        maria_cnf = json.load(maria_cnf_FD)
        maria_cnf_FD.close()
        try:
            fast_maria_conn = mysql.connector.connect(host=maria_cnf['host'],user=maria_cnf['user'],
                password=maria_cnf['password'],database='git_info')
        except mysql.connector.Error as e:
            print('Database Error, Exiting server')
            print(e)
            fast_maria_conn = None
    api_req = await request.json()
    # hasattr, getattr
    if '_C' in api_req and api_req['_C'] == 'RegisterGitRepo':
        if not 'groupId' in api_req:
            api_req['groupId'] = None
        if not 'exercisePath' in api_req:
            api_req['exercisePath'] = None
        if fast_maria_conn:
            try:
                maria_cur = fast_maria_conn.cursor()
                maria_cur.execute('''INSERT INTO depot 
                    VALUES (%s,%s,%s,%s,%s,%s)''',
                    (api_req['repoUrl'],api_req['semesterId'],api_req['courseId'],api_req['groupId'],api_req['studentId'],api_req['exercisePath']))
                fast_maria_conn.commit()
            except mysql.connector.errors.IntegrityError:
                print('Duplicate depot')
                return Response(status_code = status.HTTP_304_NOT_MODIFIED)
#  {
#      "_C":"RegisterGitRepo",
#      "repoUrl":"https://github.com/patate/atelier.git",
#      "semesterId":"H2021",
#      "studentId":"1234567",
#      "courseId":"3C6",
#      "groupId":"01",
#      "exercisePath":"/etape1/atelier",
#  }

@app.post("/_git_hook")
async def read_hook(request: Request, response: Response):
    global fast_lite_conn
    if fast_lite_conn is None:
        try:
            fast_lite_conn = sqlite3.connect('data/git_tasks.db')
        except sqlite3.Error as e:
            print('Database Error, Exiting server')
            print(e)
            fast_lite_conn = None
    if fast_lite_conn:
        hook = await request.json()
        # Process Hook
#        print(hook)
        req = {}
        req['type'] = 'hook'
        req['depot'] = None
        if 'repository' in hook:
            if 'git_http_url' in hook['repository']:                # GITLAB
                req['depot'] = hook['repository']['git_http_url']
            elif 'clone_url' in hook['repository']:                 # GITHUB
                req['depot'] = hook['repository']['clone_url']
        elif 'resource' in hook:
            if 'repository' in hook['resource'] and \
                'remoteUrl' in hook['resource']['repository']:      # AZURE
                req['depot'] = hook['resource']['repository']['remoteUrl']
        # Add to DB
        if req['depot']:
            cur = fast_lite_conn.cursor()
            cur.execute('''INSERT INTO tasks(priority,req_date,request) 
                VALUES (?,DateTime('now','localtime'),?)''',
                (5,json.dumps(req)))
            cur.close()
            fast_lite_conn.commit()
        else:
            print('INVALID HOOK')
#        print(request)
    else:
        response.status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
        print('Database Error!')
#    return {"item_id": item_id, "q": q}
#    cur.lastrowid

# @app.on_event('shutdown')
# def cleanup():

def pull_depot():
    maria_cnf_FD = open('db_conf.json')
    maria_cnf = json.load(maria_cnf_FD)
    maria_cnf_FD.close()

    conn = None
    conn2 = None
    try:
        conn = sqlite3.connect('data/git_tasks.db')
        conn2 = mysql.connector.connect(host=maria_cnf['host'],user=maria_cnf['user'],
            password=maria_cnf['password'],database='git_info')
        cur = conn.cursor()
        cur2 = conn2.cursor()
        while True:
            cur.execute('SELECT * FROM tasks WHERE ans_date IS NULL \
                ORDER BY priority,req_date')
            print(cur)
            row = cur.fetchone()
            if row:
                print(row)
                # Extract request type
                try:
                    request = json.loads(row[3])
                # If hook, find depot in student DB
                    if 'type' in request and request['type'] == 'hook':
                        cur2.execute('SELECT * FROM depot WHERE url_depot=%s',(request['depot'],))
                        record = cur2.fetchone()
                        print(record)
                        if record:
                            depotDir = record[0].split('/')
                            depotDir = depotDir[len(depotDir)-1].replace('.git','')
                            print(depotDir)                            
                        else:
                            print('DEPOT NOT FOUND: ' + str(request['depot']))
                    else:
                        print('INVALID REQUEST TYPE: ' + str(request))
                #   Not there... Error
                # Fetch / Clone depot
                #   Not possible ... Error
                # Extract history to depotDB
                    cur.execute('UPDATE tasks \
                        SET answer = ?, ans_date = DateTime("now","localtime") \
                        WHERE task_id = ?', ('DONE',row[0]))
                    conn.commit()
                except json.decoder.JSONDecodeError:
                    print('BAD REQUEST: ' + row[3])
                    cur.execute('UPDATE tasks \
                        SET answer = ?, ans_date = DateTime("now","localtime") \
                        WHERE task_id = ?', ('BAD REQUEST',row[0]))
                    conn.commit()
            time.sleep(15)
        conn.close()
    except sqlite3.Error as e:
        print('Database Error, Exiting server')
        print(e)
    except KeyboardInterrupt:
        pass
    finally:
        if conn:
            conn.close()
        print('STOP')

# commandes GIT:
# rep = git.Repo('420-zf5-demo')
# >>> stat = rep.commit('HEAD~1').stats
# >>> stat.files OU stats.total
# >>> for com in rep.iter_commits():
# >>> for com in rep.iter_commits('4a2241cbab81f90e2b2a619d07a21cbb0054220e'):
# >>> for com in rep.iter_commits('4a2241cbab81f90e2b2a619d07a21cbb0054220e..'):
# >>> for com in rep.iter_commits(reverse=True):
# >>> for com in rep.iter_commits('4a2241cbab81f90e2b2a619d07a21cbb0054220e..',reverse=True):
# ...     print(com.stats.files)
# ...     print(com)
# >>> rep.git.checkout()
# >>> rep.git.checkout('HEAD~1')
# >>> rep.git.checkout('-')
# >>> rep.git.status()
# # Verifier si le depot existe
# Si oui, Pull, sinon, clone
#        depotPath = os.path.join(configJSON['depotDir'], row[1])
#        if not os.path.isdir(depotPath) :
#            print(depotPath + ' not exists, cloning!')
#            git.Repo.clone_from(row[3],depotPath)
#        else :
#            print(depotPath + ' exists, pulling!')
#            try :
#                git.Repo(depotPath).remotes.origin.pull()

#    print(queue)
#    depot = None
#    depot_todo = []
#    try:
#        while depot != 'stop':
#            while depot != None and depot != 'stop':
#                if depot not in depot_todo:
#                    depot_todo.append(depot)
#            time.sleep(1)
#    except KeyboardInterrupt:
#        print(depot_todo)
#        pass
#    print('STOP')

if __name__=="__main__":
    # Clean SQLite DB
    conn = None
    try:
        conn = sqlite3.connect('data/git_tasks.db')
        cur = conn.cursor()
        cur.execute('DROP TABLE IF EXISTS tasks;')
        cur.execute('''CREATE TABLE tasks (
                        task_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        priority INTEGER,
                        req_date TEXT,
                        request TEXT,
                        ans_date TEXT,
                        answer TEXT,
                        ack_date TEXT);''')
# TEST DATA - Begin
        cur.execute('''INSERT INTO tasks(priority,req_date,request) 
            VALUES (5,DateTime('now','localtime'),"req1")''')
#        cur.execute('''INSERT INTO tasks(priority,req_date,request) 
#            VALUES (5,DateTime('now','localtime'),"req2")''')
#        cur.execute('''INSERT INTO tasks(priority,req_date,request) 
#            VALUES (9,DateTime('now','localtime'),"req3")''')
#        cur.execute('''INSERT INTO tasks(priority,req_date,request) 
#            VALUES (1,DateTime('now','localtime'),"req4")''')
#        cur.execute('''INSERT INTO tasks(priority,req_date,request) 
#            VALUES (5,DateTime('now','localtime'),"req5")''')
        conn.commit()
        conn2 = mysql.connector.connect(user='root',password='test',database='git_info')
        cur2= conn2.cursor()
        cur2.execute('DELETE FROM depot')
        conn2.commit()
        cur2.execute('''INSERT INTO depot 
            VALUES ('https://gitlab.com/LeducNic/coursmo.git','H21','420ZF5',2,2055573, null)''')
        cur2.execute('''INSERT INTO depot 
            VALUES ('https://github.com/LeducNic/TestZF5.git','A20','420ZC6',1,2044473, null)''')
        cur2.execute('''INSERT INTO depot 
            VALUES ('https://dev.azure.com/nleduc/TestZC6/_git/TestZC6','H21','420ZF5',2,1933325, null)''')
        cur2.execute('''INSERT INTO depot 
            VALUES ('https://github.com/LeducNic/TestZF52.git','H21','420ZF5',1,1822273, null)''')
#        cur2.execute('''INSERT INTO depot 
#            VALUES ('https://gitlab.com/LeducNic/coursmo2.git','H21','420C65',2,1788895, null)''')
        conn2.commit()
# TEST DATA - End
        conn.close()
        conn = None
        api.my_print.process()
    # Start other process with a Queue
        p = Process(target=pull_depot)
        p.start()
        uvicorn.run("git_server:app", host='192.168.5.49')
    # Stop other process
        p.join()
        print('DONE')
    except sqlite3.Error as e:
        print('Database Error, Exiting GIT server')
        print(e)
    finally:
        if conn:
            conn.close()
    

