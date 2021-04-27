import time
import os
import sqlite3
import mysql.connector
import json
import utils.normalize_data
import git
import utils.task_utils
import utils.data_matcher

def process(task_req, maria_conn, lite_conn):
    return hook_task(task_req['depot'], maria_conn)

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
def update_commit_db(semester, course, group, repo_path, depot, depotPath, maria_conn):
    repo = git.Repo(depotPath)
    cur = maria_conn.cursor()
    # Trouver dernier commit du depot
    cur.execute('SELECT commit_id FROM commit WHERE repo_url=%s ORDER BY commit_date DESC',(depot,))
    last_commit = cur.fetchone()
    print(last_commit)
    if last_commit:
        last_commit = last_commit[0] + '..'
        cur.fetchall()
#    last_commit = '4091faf65b9d875d3c23dc7b373e02ae858b21b7' + '..'
    ex_list, rp_list, fp_list, kw_list = utils.task_utils.find_exercise_for_course(semester,course,group,maria_conn)
    repo = git.Repo(depotPath)
    for commit in repo.iter_commits(last_commit, reverse = True):
#        print(commit)
        commit_id = commit.hexsha
        commit_sum = commit.summary
        commit_msg = commit.message
        commit_date = commit.authored_datetime
        completed_ex = utils.data_matcher.find_exercise_from_keyword(ex_list, kw_list, commit_sum)
        # None # TODO: Match des exercices keyword avec summary
        cur.execute('INSERT INTO commit VALUES (%s,%s,%s,%s,%s,%s)',
            (depot, commit_id, commit_date, commit_sum, commit_msg, completed_ex))
        maria_conn.commit()
        for file_name,stat in commit.stats.files.items():
            insert = stat['insertions']
            delete = stat['deletions']
            effort = insert + delete # TODO: Utiliser JPlag pour calculer l'effort
            exercice = utils.data_matcher.find_exercise_from_path(ex_list, rp_list, fp_list, repo_path, file_name)
            # '/' # TODO: Determiner le chemin de l'exercice qui correspond au fichier
#            print(file_name + ' : ' + exercice)
            cur.execute('INSERT INTO commit_file VALUES (%s,%s,%s,%s,%s,%s,%s)',
                (depot, commit_id, file_name, insert, delete, effort, exercice))
            maria_conn.commit()
# delete from element where (url_depot, commit_id) in (select url_depot, commit_id from commit where commit_date > '2020-09-30');
# delete from commit where commit_date > '2020-09-30';
    # Pour tous les commit suivants
    # Extraire date et message
    # Pour chaque fichier du Commit
    # Extraire les infos
    # Calculer l'effort (utiliser diff pour recuperer les fichiers)

def hook_task(depot, maria_conn):
    maria_cur = maria_conn.cursor()
    maria_cur.execute('SELECT * FROM repository WHERE repo_url=%s',(depot,))
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
            answer = update_commit_db(record[2],record[3],record[4],record[6],depot, depotPath, maria_conn)
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
