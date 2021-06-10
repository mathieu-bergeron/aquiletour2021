from fastapi import Response, status
from fastapi.responses import JSONResponse

#  {
#       "_C": "RegisterGitRepoForCourse",
#       "teacherId": "nicolas.leduc"
#       "semesterId": "H2021",
#       "courseId": "IntroProg",
#       "repoPath": "/tps/depotgit"
#   }
def process(api_req, maria_conn, lite_conn):
    response = JSONResponse()
    response.status_code = status.HTTP_200_OK
    return response

