import re

# lettre + 4 chiffres -> 'H2021'
# Accept: hiver 2021, hiver-2021, hiver2021, hiver21, h2021, h21
def normalize_session(session_arg):
    match = re.match('^([A-Z])[A-Z]*[ -]?(\d\d)?(\d\d)$', session_arg.upper())
    if match:
        if match.group(2) is None:
            session_str = match.group(1) + '20' + match.group(3)
        else:
            session_str = match.group(1) + match.group(2) + match.group(3)
    else:
        session_str = None
    return session_str

# nom.prof/idCours : 'mathieu.bergeron/StruDon'
# Accept: ???
def normalize_courseId(course_arg):
    match = re.match('^([A-Za-z0-9.-]+)/([A-Za-z0-9_.-]+)$', course_arg)
    if match:
        course_str = match.group(1).lower() + '/' + match.group(2)
    else:
        course_str = None
    return course_str

# 2 caracteres (leading 0) : '02'
# Accept: 1-99, '1'-'99'
def normalize_group(group_arg):
    group_str = str(group_arg).rjust(2, '0')
    if len(group_str) != 2 or group_str == '00' or not re.match('^\d\d$', group_str):
        group_str = None
    return group_str

# nom.etudiant ou DA
# Accept: ???
def normalize_studentId(student_arg):
    match = re.match('^[a-z0-9.-]+$', str(student_arg).lower())
    if match:
        student_str = str(student_arg).lower()
    else:
        student_str = None
    return student_str

def transpose(list_of_lines):
    return list(map(list, zip(*list_of_lines)))


# Some Unit Test
def test_session():
    print(str(normalize_session('')) + ' = None')
    print(str(normalize_session('H20O20')) + ' = None')
    print(str(normalize_session('H+20')) + ' = None')
    print(normalize_session('hiver2015') + ' = H2015')
    print(normalize_session('automne 2017') + ' = A2017')
    print(normalize_session('PRINTEMPS-1993') + ' = P1993')
    print(normalize_session('E2021') + ' = E2021')
    print(normalize_session('a-2010') + ' = A2010')
    print(normalize_session('p 2009') + ' = P2009')
    print(normalize_session('A23') + ' = A2023')
    print(normalize_session('E 25') + ' = E2025')
    print(normalize_session('Hiver 22') + ' = H2022')

# TODO: Ajouter des tests unitaires
def test_course():
    print(str(normalize_courseId('')) + ' = None')
    print(str(normalize_courseId('42OZf5')) + ' = None')
    print(str(normalize_courseId('ZD4+MO')) + ' = None')
    print(normalize_courseId('mathieu.bergeron/StruDon') + ' = mathieu.bergeron/StruDon')
    print(normalize_courseId('Nicolas.Leduc/420-ZC6') + ' = nicolas.leduc/420-ZC6')

def test_group():
    print(str(normalize_group('')) + ' = None')
    print(str(normalize_group(0)) + ' = None')
    print(str(normalize_group('A')) + ' = None')
    print(str(normalize_group('3B')) + ' = None')
    print(str(normalize_group('-20')) + ' = None')
    print(str(normalize_group('105')) + ' = None')
    print(str(normalize_group(-20)) + ' = None')
    print(str(normalize_group(105)) + ' = None')
    print(normalize_group('2') + ' = 02')
    print(normalize_group('20') + ' = 20')
    print(normalize_group(5) + ' = 05')
    print(normalize_group(61) + ' = 61')

# TODO: Ajouter des tests unitaires
def test_student():
    print(str(normalize_studentId('')) + ' = None')
    print(normalize_studentId(0) + ' = 0')
    print(normalize_studentId('12345678') + ' = 12345678')
    print(normalize_studentId(12345678) + ' = 12345678')
    print(normalize_studentId('nicolas.leduc') + ' = nicolas.leduc')
    print(normalize_studentId('Pierre-Paul.Bois-Page') + ' = pierre-paul.bois-page')

def test_transpose():
    print(transpose([[1,2,3],[4,5,6],[7,8,9],['a','b','c']]))

if __name__=="__main__":
    test_session()
    test_course()
    test_group()
    test_student()
    test_transpose()
