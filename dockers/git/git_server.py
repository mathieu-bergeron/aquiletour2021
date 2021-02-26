# from typing import Optional
from fastapi import FastAPI, Request
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
async def read_hook(request: Request):
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
        pass # Return server error
#    print(request)
#    print(hook['object_kind'])
#    return {"item_id": item_id, "q": q}

# @app.on_event('shutdown')
# def cleanup():

def pull_depot():
    conn = None
    try:
        conn = sqlite3.connect('data/git_tasks.db')
        while True:
            time.sleep(1)
#        cur = conn.cursor()
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
    

