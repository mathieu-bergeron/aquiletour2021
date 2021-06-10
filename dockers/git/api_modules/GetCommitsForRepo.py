from fastapi import Response, status
from fastapi.responses import JSONResponse
from datetime import datetime
import mysql.connector
import utils.normalize_data
import utils.task_utils

#   {
#       "_C": "GetCommitsForRepo",
#       "teacherId": "mathieu.bergeron",
#       "courseId": "StruDon",
#       "semesterId": "H2021",
#       "groupId": "01",
#       "studentId": "1234500",
#       "repoPath": "/semaine01/depotgit"
#       "fromDate" : 1615215942,
#       "toDate" : 1615216442,
#       "recursive": true
#   }
# NOTE:
# / : les commits de tous les dépôts
# /semaine01 : les commits des dépôts qui match /semaine01
# recursive est à False par défaut s'il est absent de la requête
# fromDate/toDate sont à -1 si non-désiré
# Réponse: un modèle CommitListModel
def process(api_req, maria_conn, lite_conn):
    if not 'groupId' in api_req:
        api_req['groupId'] = '__NONE__'
    if maria_conn:
        try:
            semester = utils.normalize_data.normalize_semesterId(api_req['semesterId'])
            course = api_req['courseId']
            teacher = api_req['teacherId']
            group = api_req['groupId']
            student = utils.normalize_data.normalize_studentId(api_req['studentId'])
            re_path = api_req['repoPath']
            sql_request = 'SELECT co.*, cf.file_path, cf.effort, co.exercise_path AS co_ex_path, cf.exercise_path AS cf_ex_path \
                            FROM repository re JOIN commit co USING (repo_url) JOIN commit_file cf USING (repo_url,commit_id) \
                            WHERE re.session_id = %s AND re.student_id = %s '
            sql_params = [semester, student]
            if api_req.get('courseId','*') != '*':
                course = utils.normalize_data.normalize_courseId(course)
                teacher = utils.normalize_data.normalize_teacherId(teacher)
                group = utils.normalize_data.normalize_groupId(group)
                sql_request += 'AND re.course_id = %s AND re.teacher_id = %s '                  # Seulement si courseId != '*'
                sql_params.extend([course, teacher])
            if api_req.get('recursive', False):
                sql_request += 'AND re.repo_path LIKE %s '      # Recursif
                sql_params.append(re_path+'%')
            else:
                sql_request += 'AND re.repo_path = %s '         # Exact match
                sql_params.append(re_path)
            if api_req.get('fromDate',-1) > 0:
                sql_request += 'AND co.commit_date >= %s '
                sql_params.append(datetime.fromtimestamp(api_req['fromDate']))
            if api_req.get('toDate',-1) > 0:
                sql_request += 'AND co.commit_date <= %s '
                sql_params.append(datetime.fromtimestamp(api_req['toDate']))
            sql_request += 'ORDER BY co.commit_date, co.commit_id'

            cur = maria_conn.cursor(dictionary = True)
            cur.execute(sql_request, tuple(sql_params))
            lines = cur.fetchall()
            body = {}
            body['_C'] = 'CommitListModel'
            body['semesterId'] = semester
            body['courseId'] = course
            body['teacherId'] = teacher
            body['groupId'] = group
            body['studentId'] = student
            body['fromDate'] = api_req['fromDate']
            body['toDate'] = api_req['toDate']
            body['commits'] = utils.task_utils.prepare_commit_list(lines)
            response = JSONResponse(content = body)
            response.status_code = status.HTTP_200_OK
            maria_conn.rollback()
        except mysql.connector.errors.IntegrityError:
            maria_conn.rollback()
            print('Duplicate depot or invalid data')
            response = Response()
            response.status_code = status.HTTP_304_NOT_MODIFIED
        except mysql.connector.errors.DataError:
            print('Data format error')
            response = JSONResponse()
            response.status_code = status.HTTP_400_BAD_REQUEST
        except KeyError:
            maria_conn.rollback()
            response = JSONResponse()
            response.status_code = status.HTTP_400_BAD_REQUEST
    else:
        response = JSONResponse()
        response.status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
    return response
