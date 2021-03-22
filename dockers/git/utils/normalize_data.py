import re

# lettre + 2 chiffres -> 'H21'
# Accept: hiver 2021, hiver-2021, hiver2021, hiver21, h2021, h21
def normalize_session(session_arg):
    match = re.match('^([A-Z])[A-Z]*[ -]?(\d\d)?(\d\d)$', session_arg.upper())
    if match:
        session_str = match.group(1) + match.group(3)
    else:
        session_str = None
    return session_str

# 3 chiffres + 3 alpha : '420-ZC5'
# Accept: ZC5, ZC5-MO, 420-ZC5, 420-ZC5-MO, 420ZC5-MO, 420ZC5MO
def normalize_courseId(course_arg):
    match = re.match('^(\d\d\d)?[ -]?([A-Z0-9][A-Z0-9][A-Z0-9])[ -]?(MO)?$', str(course_arg).upper())
    if match:
        if match.group(1):
            course_str = match.group(1)
        else:
            course_str = '420'
        course_str += '-' + match.group(2)
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

# 7 chiffres (DA): '1945612'
# Accept: 9 chiffres ou 7 chiffres
def normalize_studentId(student_arg):
    match = re.match('^(\d\d)?(\d\d\d\d\d\d\d)$', str(student_arg))
    if match:
        student_str = match.group(2)
    else:
        student_str = None
    return student_str


# Some Unit Test
def test_session():
    print(str(normalize_session('')) + ' = None')
    print(str(normalize_session('H20O20')) + ' = None')
    print(str(normalize_session('H+20')) + ' = None')
    print(normalize_session('hiver2015') + ' = H15')
    print(normalize_session('automne 2017') + ' = A17')
    print(normalize_session('PRINTEMPS-1993') + ' = P93')
    print(normalize_session('E2021') + ' = E21')
    print(normalize_session('a-2010') + ' = A10')
    print(normalize_session('p 2009') + ' = P09')
    print(normalize_session('A23') + ' = A23')
    print(normalize_session('E 25') + ' = E25')
    print(normalize_session('Hiver 22') + ' = H22')

def test_course():
    print(str(normalize_courseId('')) + ' = None')
    print(str(normalize_courseId('42OZf5')) + ' = None')
    print(str(normalize_courseId('ZD4+MO')) + ' = None')
    print(normalize_courseId('3C5') + ' = 420-3C5')
    print(normalize_courseId('Zd4-MO') + ' = 420-ZD4')
    print(normalize_courseId('zc5 MO') + ' = 420-ZC5')
    print(normalize_courseId('201-NYA') + ' = 201-NYA')
    print(normalize_courseId('202nyB') + ' = 202-NYB')
    print(normalize_courseId('201 105') + ' = 201-105')
    print(normalize_courseId('420-346-MO') + ' = 420-346')
    print(normalize_courseId('201ZD4MO') + ' = 201-ZD4')
    print(normalize_courseId('420zf5-mo') + ' = 420-ZF5')

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

def test_student():
    print(str(normalize_studentId('')) + ' = None')
    print(str(normalize_studentId(0)) + ' = None')
    print(str(normalize_studentId('12345678')) + ' = None')
    print(str(normalize_studentId(12345678)) + ' = None')
    print(str(normalize_studentId('12E456789')) + ' = None')
    print(str(normalize_studentId('-3456789')) + ' = None')
    print(normalize_studentId('1552684') + ' = 1552684')
    print(normalize_studentId(1789340) + ' = 1789340')
    print(normalize_studentId('201138725') + ' = 1138725')
    print(normalize_studentId(202177799) + ' = 2177799')

if __name__=="__main__":
    test_session()
    test_course()
    test_group()
    test_student()
