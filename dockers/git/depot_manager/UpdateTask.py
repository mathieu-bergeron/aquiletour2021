import json
import mysql.connector
import utils.normalize_data
import utils.data_matcher
import utils.task_utils
import utils.net_utils

#  {
#      "_C": "UpdateTask",
#      "semesterId": "H2021",
#      "courseId": "IntroProg",
#      "teacherId": "nicolas.leduc"
#      "groupId": "01",
#  }
def process(task_req, maria_conn, lite_conn):
    cur = maria_conn.cursor(dictionary = True)
    if not 'groupId' in task_req:
        task_req['groupId'] = '__NONE__'
    semester = utils.normalize_data.normalize_semesterId(task_req['semesterId'])
    course = utils.normalize_data.normalize_courseId(task_req['courseId'])
    teacher = utils.normalize_data.normalize_teacherId(task_req['teacherId'])
    group = utils.normalize_data.normalize_groupId(task_req['groupId'])
    ex_list, rp_list, fp_list, kw_list = utils.task_utils.find_exercise_for_course(semester,course,teacher,group,maria_conn)
    # GROUP_ID not used in the search
    cur.execute('''SELECT re.repo_url, co.commit_id, cf.file_path, re.repo_path, co.summary, 
                        co.exercise_path AS co_ex_path, cf.exercise_path AS cf_ex_path,
                        re.group_id, re.student_id 
                    FROM repository re, commit co, commit_file cf 
                    WHERE re.repo_url = co.repo_url AND co.repo_url = cf.repo_url AND co.commit_id = cf.commit_id
                    AND re.session_id=%s AND re.course_id=%s AND re.teacher_id=%s
                    ORDER BY re.repo_url, co.commit_id''',
                    (semester, course, teacher))
    cur_commitId = ''
    cur_repoURL = ''
    modelUpdate = False
    upd_msg = {}
    upd_msg['_C'] = 'OnCommitModelUpdate'
    upd_msg['semesterId'] = semester
    upd_msg['courseId'] = course
    upd_msg['teacherId'] = teacher
    for row in cur.fetchall():
#        print(cur_commitId + ' : ' + row[1])
        if cur_repoURL != row['repo_url']:
            cur_repoURL = row['repo_url']
            if modelUpdate:
                modelUpdate = False
                maria_conn.commit()
                utils.net_utils.send_message(upd_msg)
        if cur_commitId != row['commit_id']: # new commit, check the summary
            cur_commitId = row['commit_id']
            new_co_exPath = utils.data_matcher.find_exercise_from_keyword(ex_list, kw_list, row['summary'])
            print('COMMIT: ' + str(new_co_exPath) + " : " + str(row['co_ex_path']))
            if new_co_exPath != row['co_ex_path']:
                print('UPDATING DATABASE')
                # TODO: Send message only if it is the last commit associated with the exercise path
                message = {}
                message['_C'] = 'OnExerciseCompletedUpdate'
                message['semesterId'] = semester
                message['courseId'] = course
                message['teacherId'] = teacher
                message['groupId'] = row['group_id']
                message['studentId'] = row['student_id']
                if row['co_ex_path']:
                    message['exercisePath'] = row['co_ex_path']
                    message['exerciseCompleted'] = 'False'
                    utils.net_utils.send_message(message)
                cur.execute('UPDATE commit \
                    SET exercise_path = %s \
                    WHERE repo_url= %s AND commit_id = %s', (new_co_exPath, row['repo_url'], row['commit_id']))
                if new_co_exPath:
                    message['exercisePath'] = new_co_exPath
                    message['exerciseCompleted'] = 'True'
                    utils.net_utils.send_message(message)
        # check each file
        new_cf_exPath = utils.data_matcher.find_exercise_from_path(ex_list,rp_list,fp_list, row['repo_path'], row['file_path'])
        print('FILE: ' + new_cf_exPath + " : " + row['cf_ex_path'])
        if new_cf_exPath != row['cf_ex_path']:
            print('UPDATING DATABASE')
            if not modelUpdate:
                modelUpdate = True
                upd_msg['groupId'] = row['group_id']
                upd_msg['studentId'] = row['student_id']
            cur.execute('UPDATE commit_file \
                SET exercise_path = %s \
                WHERE repo_url= %s AND commit_id = %s AND file_path = %s', (new_cf_exPath, row['repo_url'], row['commit_id'], row['file_path']))
    maria_conn.commit()
    if modelUpdate:
        modelUpdate = False
        utils.net_utils.send_message(upd_msg)
    return (True, json.dumps({'status': 'DONE'}))
