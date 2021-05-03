import json
import mysql.connector
import utils.normalize_data
import utils.data_matcher

#  {
#      "_C": "UpdateTask",
#      "semesterId": "H2021",
#      "courseId": "nicolas.leduc/IntroProg",
#      "groupId": "01",
#  }
def process(task_req, maria_conn, lite_conn):
    cur = maria_conn.cursor()
    semester = utils.normalize_data.normalize_session(task_req['semesterId'])
    course = utils.normalize_data.normalize_courseId(task_req['courseId'])
    group = utils.normalize_data.normalize_group(task_req['groupId'])
    ex_list, rp_list, fp_list, kw_list = utils.task_utils.find_exercise_for_course(semester,course,group,maria_conn)
    cur.execute('''SELECT re.repo_url,co.commit_id,cf.file_path, re.repo_path,co.summary, co.exercise_path,cf.exercise_path
                    FROM repository re, commit co, commit_file cf 
                    WHERE re.repo_url = co.repo_url AND co.repo_url = cf.repo_url AND co.commit_id = cf.commit_id
                    AND re.session_id=%s AND re.course_id=%s AND re.group_id=%s
                    ORDER BY re.repo_url, co.commit_id''',
                    (semester, course, group))
    cur_commitId = ''
    for row in cur.fetchall():
        print(cur_commitId + ' : ' + row[1])
        if cur_commitId != row[1]: # new commit, check the summary
            cur_commitId = row[1]
            new_co_exPath = utils.data_matcher.find_exercise_from_keyword(ex_list, kw_list, row[4])
            print('COMMIT: ' + str(new_co_exPath) + " : " + str(row[5]))
            if new_co_exPath != row[5]:
                print('UPDATING DATABASE')
                cur.execute('UPDATE commit \
                    SET exercise_path = %s \
                    WHERE repo_url= %s AND commit_id = %s', (new_co_exPath, row[0], row[1]))
        # check each file
        new_cf_exPath = utils.data_matcher.find_exercise_from_path(ex_list,rp_list,fp_list, row[3], row[2])
        print('FILE: ' + new_cf_exPath + " : " + row[6])
        if new_cf_exPath != row[6]:
            print('UPDATING DATABASE')
            cur.execute('UPDATE commit_file \
                SET exercise_path = %s \
                WHERE repo_url= %s AND commit_id = %s AND file_path = %s', (new_cf_exPath, row[0], row[1], row[2]))
    maria_conn.commit()
    return (True, json.dumps({'status': 'DONE'}))
