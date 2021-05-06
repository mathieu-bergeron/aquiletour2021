import time
import os
import sqlite3
import mysql.connector
import json
import utils.normalize_data
import git
import importlib

def process_requests():
    maria_conn = None
    lite_conn = None
        
    try:
        maria_cnf_FD = open('db_conf.json')
        maria_cnf = json.load(maria_cnf_FD)
        maria_cnf_FD.close()
        if not os.path.isdir('depot'):
            print('ERROR: Depot dir not found')
            return
        lite_conn = sqlite3.connect('data/git_tasks.db')
        maria_conn = mysql.connector.connect(host=maria_cnf['host'],user=maria_cnf['user'],
            password=maria_cnf['password'],database='git_info')
        lite_cur = lite_conn.cursor()
#        maria_cur = maria_conn.cursor()
        while True:
            lite_cur.execute('SELECT * FROM tasks WHERE ans_date IS NULL \
                ORDER BY priority,req_date')
            print(lite_cur)
            row = lite_cur.fetchone()
            if row:
                print(row)
                # Extract request type
                try:
                    request = json.loads(row[3])
                    task_mod = importlib.import_module('depot_manager.' + request['_C'])
                    result = task_mod.process(request, maria_conn, lite_conn)
                except json.decoder.JSONDecodeError:
                    print('BAD REQUEST FORMAT: ' + row[3])
                    result = (True, json.dumps({'status': 'BAD REQUEST FORMAT'}))
                except ModuleNotFoundError:
                    print('INVALID REQUEST TYPE: ' + str(request))
                    result = (True, json.dumps({'status': 'INVALID REQUEST TYPE'}))
                except KeyError:
                    print('BAD REQUEST FORMAT: ' + str(request))
                    result = (True, json.dumps({'status': 'BAD REQUEST FORMAT'}))
                lite_cur.execute('UPDATE tasks \
                    SET answer = ?, ans_date = DateTime("now","localtime") \
                    WHERE task_id = ?', (result[1],row[0]))
                if result[0]:
                    lite_cur.execute('UPDATE tasks \
                        SET ack_date = DateTime("now","localtime") \
                        WHERE task_id = ?', (row[0],))
                lite_conn.commit()
            time.sleep(15)
        lite_conn.close()
        maria_conn.close()
    except sqlite3.Error as e:
        print('Database Error, Exiting server')
        print(e)
        lite_conn = None
    except mysql.connector.Error as e:
        print('Database Error, Exiting server')
        print(e)
        maria_conn = None
    except json.JSONDecodeError:
        print('ERROR Reading json config file')
    except KeyboardInterrupt:
        pass
    finally:
        if lite_conn:
            lite_conn.close()
        if maria_conn:
            maria_conn.close()
        print('STOP')
