import mysql.connector
import utils

def find_exercise_for_course(semester, course, group, maria_conn):
    cur = maria_conn.cursor()
    semester = utils.normalize_data.normalize_session(semester)
    course = utils.normalize_data.normalize_courseId(course)
    group = utils.normalize_data.normalize_group(group)
    cur.execute('''SELECT exercise_path,repo_path,file_path,completion_kw
                    FROM exercise 
                    WHERE session_id=%s AND course_id=%s AND group_id=%s''',
                    (semester, course, group))
    lines = cur.fetchall()
    cols = utils.normalize_data.transpose(lines)
#    print('NB EX = ' + str(len(lines)))
    return cols[0], cols[1], cols[2], cols[3]