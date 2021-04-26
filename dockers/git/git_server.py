# from typing import Optional
from fastapi import FastAPI, Request, Response, status
from fastapi.responses import JSONResponse
from multiprocessing import Process
import time
import os
import importlib
import sys
import uvicorn
import sqlite3
import mysql.connector
import json
import api_modules
import depot_manager.task_processor

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

@app.post("/_git_resp")
async def read_api(request: Request):
    body = {'type':'hook','depot':'ici'}
    response = Response()
#    response = JSONResponse(content=body)
#    response.body = 'test123'.encode('utf-8')
    response.status_code = status.HTTP_304_NOT_MODIFIED
#    response.init_headers()
    body = {'type':'hook','depot':'labo'}
    return response

@app.post("/_git_api")
async def read_api(request: Request):
    global fast_maria_conn
    global fast_lite_conn
    if fast_lite_conn is None:
        try:
            fast_lite_conn = sqlite3.connect('data/git_tasks.db')
        except sqlite3.Error as e:
            print('Database Error, Exiting server')
            print(e)
            fast_lite_conn = None
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
    if fast_maria_conn and fast_lite_conn:
        api_req = await request.json()
        try:
            api_mod = importlib.import_module('api_modules.' + api_req['_C'])
            response = api_mod.process(api_req, fast_maria_conn, fast_lite_conn)
        except ModuleNotFoundError:
            response = JSONResponse()
            response.status_code = status.HTTP_501_NOT_IMPLEMENTED
        except KeyError:
            response = JSONResponse()
            response.status_code = status.HTTP_400_BAD_REQUEST
    else:
        response = JSONResponse()
        response.status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
    return response

@app.post("/_git_hook")
async def read_hook(request: Request):
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
        req['_C'] = 'HookTask'
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
            cur.execute('SELECT * FROM tasks\
                WHERE ans_date IS NULL AND request=?',(json.dumps(req),))
            row = cur.fetchone()
            if row:
                body = {'request_id' : row[0]}
            else:
                cur.execute('''INSERT INTO tasks(priority,req_date,request) 
                    VALUES (?,DateTime('now','localtime'),?)''',
                    (5,json.dumps(req)))
                body = {'request_id' : cur.lastrowid}
                fast_lite_conn.commit()
            cur.close()
            response = JSONResponse(content = body)
            response.status_code = status.HTTP_200_OK
        else:
            print('INVALID HOOK')
            response = JSONResponse()
            response.status_code = status.HTTP_400_BAD_REQUEST
#        print(request)
    else:
        print('Database Error!')
        response = JSONResponse()
        response.status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
    return response
#    return {"item_id": item_id, "q": q}
#    response.body = json.dumps({'item_id': 123, 'q': 'bonjour'}).encode('utf-8')
#    cur.lastrowid

# @app.on_event('shutdown')
# def cleanup():

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
#        cur.execute('''INSERT INTO tasks(priority,req_date,request) 
#            VALUES (5,DateTime('now','localtime'),"req1")''')
#        cur.execute('''INSERT INTO tasks(priority,req_date,request) 
#            VALUES (5,DateTime('now','localtime'),"{""type"": ""hok""}")''')
#        cur.execute('''INSERT INTO tasks(priority,req_date,request) 
#            VALUES (9,DateTime('now','localtime'),"req3")''')
#        cur.execute('''INSERT INTO tasks(priority,req_date,request) 
#            VALUES (1,DateTime('now','localtime'),"req4")''')
#        cur.execute('''INSERT INTO tasks(priority,req_date,request) 
#            VALUES (5,DateTime('now','localtime'),"req5")''')
        conn.commit()
        conn2 = mysql.connector.connect(user='root',password='test',database='git_info')
        cur2= conn2.cursor()
        cur2.execute('DELETE FROM commit_file')
        cur2.execute('DELETE FROM commit')
        cur2.execute('DELETE FROM repository')
        conn2.commit()
        cur2.execute('''INSERT INTO repository 
            VALUES ('https://gitlab.com/LeducNic/coursmo.git','GL','H2021','nicolas.leduc/420-ZF5','02',2055573, '/')''')
        cur2.execute('''INSERT INTO repository 
            VALUES ('https://github.com/LeducNic/TestZF5.git','GH','A2020','nicolas.leduc/420-ZC6','01',2044473, '/')''')
        cur2.execute('''INSERT INTO repository 
            VALUES ('https://dev.azure.com/nleduc/TestZC6/_git/TestZC6','AZ','H2021','mathieu.bergeron/420-ZF5','02',1933325, '/')''')
        cur2.execute('''INSERT INTO repository 
            VALUES ('https://github.com/LeducNic/TestZF52.git','GH','H2021','mathieu.bergeron/420-ZF5','01',1822273, '/')''')
#        cur2.execute('''INSERT INTO repository 
#            VALUES ('https://gitlab.com/LeducNic/coursmo2.git','GL','H2021','alain.pilon/420-C65','02',1788895, '/')''')
        conn2.commit()
        conn2.close()
# TEST DATA - End
        conn.close()
        conn = None
    # Start other process with a Queue
        p = Process(target=depot_manager.task_processor.process_requests)
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
    

