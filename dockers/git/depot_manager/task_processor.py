import time
import sqlite3
import mysql.connector
import json

def process_requests():
    maria_conn = None
    lite_conn = None

    maria_cnf_FD = open('db_conf.json')
    maria_cnf = json.load(maria_cnf_FD)
    maria_cnf_FD.close()
    try:
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
                # If hook, find depot in student DB
                    if 'type' in request and request['type'] == 'hook':
                        result = hook_task(request['depot'], maria_conn)
                    else:
                        print('INVALID REQUEST TYPE: ' + str(request))
                        result = (True, json.dumps({'status': 'INVALID REQUEST TYPE'}))
                except json.decoder.JSONDecodeError:
                    print('BAD REQUEST FORMAT: ' + row[3])
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
    except KeyboardInterrupt:
        pass
    finally:
        if lite_conn:
            lite_conn.close()
        if maria_conn:
            maria_conn.close()
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

def hook_task(depot, maria_conn):
    maria_cur = maria_conn.cursor()
    maria_cur.execute('SELECT * FROM depot WHERE url_depot=%s',(depot,))
    record = maria_cur.fetchone()
    print(record)
    if record:
        depotDir = record[0].split('/')
        depotDir = depotDir[len(depotDir)-1].replace('.git','')
        print(depotDir)
        answer = 'DONE'
        #   Not there... Error
        # Fetch / Clone depot
        #   Not possible ... Error
        # Extract history to depotDB
    else:
        print('DEPOT NOT FOUND: ' + depot)
        answer = 'DEPOT NOT FOUND'
    return (True, json.dumps({'status': answer}))

