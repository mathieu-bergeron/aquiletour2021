import json
import mysql.connector
import sqlite3
import utils.normalize_data

def find_exercise_for_course(semester, course, teacher, group, maria_conn):
    cur = maria_conn.cursor()
    semester = utils.normalize_data.normalize_semesterId(semester)
    course = utils.normalize_data.normalize_courseId(course)
    teacher = utils.normalize_data.normalize_teacherId(teacher)
    group = utils.normalize_data.normalize_groupId(group)
    # GROUP_ID not used in the search
    cur.execute('''SELECT exercise_path,repo_path,file_path,completion_kw
                    FROM exercise 
                    WHERE session_id=%s AND course_id=%s AND teacher_id=%s''',
                    (semester, course, teacher))
    lines = cur.fetchall()
    if not lines:
        lines = [['/','/','/',None]]
        print('NO EXERCISE, ADDING DUMMY')
    if ['/','/','/',None] not in lines:
        lines.append(['/','/','/',None])
        print('NO DUMMY... ADDING')
    cols = utils.normalize_data.transpose(lines)
#    print('NB EX = ' + str(len(lines)))
    return cols[0], cols[1], cols[2], cols[3]

def prepare_commit_list(lines):
    body = {}
    body['_C'] = 'ObservableCommitList'
    body['value'] = []
    cur_commit = ''
    one_commit = None
    for one_line in lines:
        if cur_commit != one_line['commit_id']:
            cur_commit = one_line['commit_id']
            if one_commit:
                body['value'].append(one_commit)
            one_commit = {}
            one_commit['_C'] = 'Commit'
            one_commit['commitId'] = one_line['commit_id']
            one_commit['exercisePathIfCompleted'] = one_line['co_ex_path']
            one_commit['commitMessageFirstLine'] = one_line['summary']
            one_commit['commitMessage'] = one_line['message']
            one_commit['timeStamp'] = int(one_line['commit_date'].timestamp())
            one_commit['modifiedFiles'] = []
        one_file = {}
        one_file['path'] = one_line['file_path']
        one_file['estimatedEffort'] = one_line['effort']
        one_file['exercisePath'] = one_line['cf_ex_path']
        one_file['message'] = ''
        one_commit['modifiedFiles'].append(one_file)
    if one_commit:
        body['value'].append(one_commit)
    return body

def add_task(task, priority, lite_conn):
    cur = lite_conn.cursor()
    str_task = json.dumps(task, sort_keys=True)
    cur.execute('SELECT * FROM tasks\
                WHERE start_date IS NULL AND request=?',(str_task,))
    row = cur.fetchone()
    if row:
        body = {'request_id' : row[0]}
        lite_conn.rollback()
    else:
        cur.execute('''INSERT INTO tasks(priority,req_date,request) 
                    VALUES (?,DateTime('now','localtime'),?)''',
                    (priority,str_task))
        body = {'request_id' : cur.lastrowid}
        lite_conn.commit()
    cur.close()
    return body

