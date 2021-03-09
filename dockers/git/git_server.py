# from typing import Optional
from fastapi import FastAPI, Request, Response, status
from multiprocessing import Process
import time
import os
import uvicorn
import sqlite3
import json

app = FastAPI()
hookConn = None
# @app.get("/")
# def read_root():
#    return {"Hello": "World"}


# @app.get("/items/{item_id}")
# async def read_item(item_id, request: Request, q = None):
#    print(request)
#    print(await request.json())
#    return {"item_id": item_id, "q": q}

@app.post("/_GH")
async def read_hook(request: Request, response: Response):
    global hookConn
    if hookConn is None:
        try:
            hookConn = sqlite3.connect('data/git_tasks.db')
        except sqlite3.Error as e:
            print('Database Error, Exiting server')
            print(e)
            hookConn = None
    if hookConn:
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
        cur = hookConn.cursor()
        cur.execute('''INSERT INTO tasks(priority,req_date,request) 
            VALUES (?,DateTime('now','localtime'),?)''',
            (5,json.dumps(req)))
        cur.close()
        hookConn.commit()
#        print(request)
    else:
        response.status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
        print('Database Error!')
#    return {"item_id": item_id, "q": q}
#    cur.lastrowid

# @app.on_event('shutdown')
# def cleanup():

def pull_depot():
    dummyData = [
        ['H21','420ZF5',2,2055573,'https://gitlab.com/LeducNic/coursmo.git'],
        ['A20','420ZC6',1,2044473,'https://github.com/LeducNic/TestZF5.git'],
        ['H21','420ZF5',2,1933325,'https://dev.azure.com/nleduc/TestZC6/_git/TestZC6'],
        ['H21','420ZF5',1,1822273,'https://github.com/LeducNic/TestZF52.git'],
        ['H21','420C65',2,1788895,'https://gitlab.com/LeducNic/coursmo2.git']
    ]
    conn = None
    try:
        conn = sqlite3.connect('data/git_tasks.db')
        cur = conn.cursor()
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
                        recordIter = iter(dummyData)
                        record = next(recordIter, None)
                        while record is not None and record[4] != request['depot']:
                            record = next(recordIter, None)
                        if record:
                            print(record)
                            depotDir = request['depot'].split('/')
                            depotDir = depotDir[len(depotDir)-1].replace('.git','')
                            print(depotDir)
                            
                        else:
                            print('DEPOT NOT FOUND: ' + request['depot'])
                    else:
                        print('INVALID REQUEST TYPE: ' + request)
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
# TEST DATA - End
        conn.close()
        conn = None
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
    

