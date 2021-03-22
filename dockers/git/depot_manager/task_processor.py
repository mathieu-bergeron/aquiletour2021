import time
import os
import sqlite3
import mysql.connector
import json
import utils.normalize_data
import git

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
# rep.commit().diff('HEAD~1')[1].a_blob.data_stream.read() # ou b_blob
#rep.commit().tree.trees[0].blobs[1].data_stream.read() # parcourir un commit...
def update_commit_db(depot, depotPath, maria_conn):
    repo = git.Repo(depotPath)
    cur = maria_conn.cursor()
    # Trouver dernier commit du depot
    cur.execute('SELECT commit_id FROM commit WHERE url_depot=%s ORDER BY commit_date DESC',(depot,))
    last_commit = cur.fetchone()
    print(last_commit)
    if last_commit:
        last_commit = last_commit[0] + '..'
        cur.fetchall()
#    last_commit = '4091faf65b9d875d3c23dc7b373e02ae858b21b7' + '..'
    repo = git.Repo(depotPath)
    for commit in repo.iter_commits(last_commit, reverse = True):
        print(commit)
        commit_id = commit.hexsha
        commit_msg = commit.message
        commit_date = commit.authored_datetime
        cur.execute('INSERT INTO commit VALUES (%s,%s,%s,%s)',
            (depot, commit_id, commit_date, commit_msg))
        maria_conn.commit()
        for file_name,stat in commit.stats.files.items():
            print(file_name)
            insert = stat['insertions']
            delete = stat['deletions']
            cur.execute('INSERT INTO element VALUES (%s,%s,%s,%s,%s)',
                (depot, commit_id, file_name, insert, delete))
            maria_conn.commit()
# delete from element where (url_depot, commit_id) in (select url_depot, commit_id from commit where commit_date > '2020-09-30');
# delete from commit where commit_date > '2020-09-30';
    # Pour tous les commit suivants
    # Extraire date et message
    # Pour chaque fichier du Commit
    # Extraire les infos
    # Calculer l'effort (utiliser diff pour recuperer les fichiers)
    pass

def hook_task(depot, maria_conn):
    maria_cur = maria_conn.cursor()
    maria_cur.execute('SELECT * FROM depot WHERE url_depot=%s',(depot,))
    record = maria_cur.fetchone()
    print(record)
    if record:
        depotDir = record[0].split('/')
        depotDir = depotDir[len(depotDir)-1].replace('.git','') + '-' + record[1]
        depotPath = os.path.join('depot',record[2],record[3],record[4],record[5],depotDir)
#        print(depotPath)
        try:
            if not os.path.isdir(depotPath) :
                print(depotPath + ' not exists, cloning!')
                git.Repo.clone_from(depot,depotPath)
            else :
                print(depotPath + ' exists, pulling!')
                git.Repo(depotPath).remotes.origin.pull()
            answer = update_commit_db(depot, depotPath, maria_conn)
            if not answer:
                answer = 'DONE'
        except git.exc.GitCommandError:
            answer = 'DEPOT not accessible on server'
        #   Not there... Error
        # Fetch / Clone depot
        #   Not possible ... Error
        # Extract history to depotDB
    else:
        print('DEPOT NOT FOUND: ' + depot)
        answer = 'DEPOT NOT FOUND'
    return (True, json.dumps({'status': answer}))

