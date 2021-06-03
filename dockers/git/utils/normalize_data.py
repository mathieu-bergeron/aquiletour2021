import re

def normalize_semesterId(semester_arg):
    return normalize_Id(semester_arg)

def normalize_courseId(course_arg):
    return normalize_Id(course_arg)

def normalize_teacherId(teacher_arg):
    return normalize_Id(teacher_arg)

def normalize_groupId(group_arg):
    return normalize_Id(group_arg)

def normalize_studentId(student_arg):
    return normalize_Id(student_arg)

def normalize_Id(id_arg):
    match = re.match('^[a-zA-Z0-9_.-]+$', str(id_arg))
    if match:
        id_str = str(id_arg)
    else:
        id_str = None
    return id_str

def transpose(list_of_lines):
    return list(map(list, zip(*list_of_lines)))


# Some Unit Test
# TODO: Ajouter des tests unitaires
def test_Id():
    print(normalize_semesterId('H2021') + ' = H2021')
    print(normalize_courseId('StruDon') + ' = StruDon')
    print(normalize_teacherId('nicolas.leduc') + ' = nicolas.leduc')
    print(str(normalize_groupId('étoile')) + ' = None')
    print(str(normalize_studentId('')) + ' = None')
    print(normalize_Id('420-ZF5') + ' = 420-ZF5')
    print(normalize_Id('Prog_Java') + ' = Prog_Java')

def test_transpose():
    print(transpose([[1,2,3],[4,5,6],[7,8,9],['a','b','c']]))

if __name__=="__main__":
    test_Id()
    test_transpose()
