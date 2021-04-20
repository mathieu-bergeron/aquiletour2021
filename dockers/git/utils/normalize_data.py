import re
import os
import Levenshtein as le

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

def clean_path(path):
    # Clean path (remove space,-,_)
    cp = re.sub('[ _-]', '', path)
    cp = cp.lower().replace('\\','/')
    # Split path by word and number
    dir_list = re.split('[/.]', cp)
    dir_part = None
    tuple_list = []
    for one_dir in dir_list:
        match = re.match('([a-z]*)([0-9]*)([a-z0-9]*)',one_dir)
        # Create 2-tuple (word, number)  number may be None if 2 consecutive words
        if match.group(1) != '':
            if dir_part:
                tuple_list.append((dir_part, None))
            dir_part = match.group(1)
        if match.group(2) != '':
            number_part = int(match.group(2))
            tuple_list.append((dir_part, str(number_part) + match.group(3)))
            dir_part = None
    return tuple_list

def match_tuples(tuple1, tuple2):
    match = False
    edits = le.editops(tuple1[0], tuple2[0])
    edits_counts = {}
    for edit,_,_ in edits:
        edits_counts[edit] = edits_counts.get(edit, 0) + 1
    inserts = edits_counts.get('insert',0) + edits_counts.get('replace',0)
    deletes = edits_counts.get('delete',0) + edits_counts.get('replace',0)
    # if only insert and max 1 delete or replace
    # OR if only delete with max 1 insert or replace
    if (inserts <= 1 or deletes <= 1) and tuple1[1] == tuple2[1]: 
        match = True
    return match

def match_path(file_path_ex, file_path_commit):
    # Clean exercise and commit file path
    tuples_exercise = clean_path(file_path_ex)
    tuples_commit = clean_path(file_path_commit)
    nb_match = 0
    # TODO: Make sure that the matching tuples are in the same order in the two paths
    for ex_tuple in tuples_exercise:
        match = False
        for c_tuple in tuples_commit:
            match |= match_tuples(ex_tuple, c_tuple)
        if match:
            nb_match += 1
    return nb_match, len(tuples_exercise)
    # max(len(tuples_exercise), len(tuples_commit))
    # For each tuple in exercicePath tuples
    #   find the matching tuple from the last match in filePath tuples
    #   if no more tuple in filePath then path doesn't match
    # return the match level (# of tuples matched) : max match is the exercice path for the file

def find_exercise(exercicePath_list, repo_path_list_ex, file_path_list_ex, repo_path_commit, file_path_commit):
    matched_exPath_list = []
    max_match_value = 0
    min_match_len = 99
    if repo_path_commit in repo_path_list_ex:
        for exPath, repoPath, filePath in zip(exercicePath_list,repo_path_list_ex, file_path_list_ex):
            if repo_path_commit == repoPath:
                nb_match, match_len = match_path(filePath, file_path_commit)
                if nb_match > max_match_value:
                    max_match_value = nb_match
                    min_match_len = match_len
                    matched_exPath_list = [exPath]
                elif nb_match == max_match_value:
                    if match_len < min_match_len:
                        min_match_len = match_len
                        matched_exPath_list = [exPath]
                    elif match_len == min_match_len:
                        matched_exPath_list.append(exPath)
    else:
        for exPath, repoPath, filePath in zip(exercicePath_list,repo_path_list_ex, file_path_list_ex):
            if repoPath.startswith(repo_path_commit):
                filePath = str(os.path.join(repoPath.split(repo_path_commit,1)[1], filePath))
            elif repo_path_commit.startswith(repoPath):
                file_path_commit = str(os.path.join(repo_path_commit.split(repoPath,1)[1], file_path_commit) )
            else:
                filePath = None
            if filePath:
                nb_match, match_len = match_path(filePath, file_path_commit)
                if nb_match > max_match_value:
                    max_match_value = nb_match
                    min_match_len = match_len
                    matched_exPath_list = [exPath]
                elif nb_match == max_match_value:
                    if match_len < min_match_len:
                        min_match_len = match_len
                        matched_exPath_list = [exPath]
                    elif match_len == min_match_len:
                        matched_exPath_list.append(exPath)

    print(matched_exPath_list)
    # If commit repo in repo_list_ex
    #   Get the exercice path that get the max match value between file paths
    # TODO: else Reparer le commit path avec le depot pour essayer de matcher
    #   Find exercices so repo_commit + file_commit contains all element of repo_ex
    #   Remove repo_ex from file path
    #   Get the exercice path that get the max match value between cleaned file paths
    pass

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

def test_exercisePath():
    ex_path_list = ['/',
                    '/Semaine 1',
                    '/Semaine 1/TP 1/Exercice 1',
                    '/Semaine 1/TP 1/Exercice 2',
                    '/Semaine 2/TravailPratique02',
                    '/Semaine 2/TravailPratique02/Ex1',
                    '/Semaine 2/TravailPratique02/Ex2',
                    '/Semaine 2/TravailPratique02/Ex3',
                    '/Semaine 3/TP 2/Etape 1',
                    '/Semaine 3/TP 2/Etape 2',
                    '/Semaine 3/TP 2/Etape 2/Exercice 1',
                    '/Semaine 3/TP 2/Etape 3/Exercice 1',
                    '/Semaine 4',
                    '/Semaine 4/Travail 4',
                    '/Semaine 4/Travail 5',
                    '/Semaine 4/Travail 5/Partie01',
                    '/Semaine 4/Travail 5/Partie02',
                    '/Semaine 4/Travail 5/Partie03',
                    '/Semaine 5',
                    '/Semaine 5/TP2/Etape 4',
                    '/Semaine 5/TP2/Etape 5',
                    '/Semaine 5/TP2/Etape 5/Exercice 1',
                    '/Semaine 5/TP2/Etape 6',
                    ]
    ex_repo_list = ['/',
                    '/',
                    '/',
                    '/',
                    '/',
                    '/',
                    '/',
                    '/',
                    '/Semaine 3/TP 2',
                    '/Semaine 3/TP 2',
                    '/Semaine 3/TP 2',
                    '/Semaine 3/TP 2',
                    '/Semaine 4',
                    '/Semaine 4',
                    '/Semaine 4/Travail 5',
                    '/Semaine 4/Travail 5',
                    '/Semaine 4/Travail 5',
                    '/Semaine 4/Travail 5',
                    '/',
                    '/',
                    '/',
                    '/',
                    '/',
                    ]
    ex_dir_list = ['/',
                    '/Semaine 1',
                    '/Semaine 1/TP 1/Exercice 1',
                    '/Semaine 1/TP 1/Exercice 2',
                    '/Sem2/TP02',
                    '/Sem2/TP02/Exercice01',
                    '/Sem2/TP02/Exercice02',
                    '/Sem2/TP02/Exercice03',
                    '/src/Etape1',
                    '/src/Etape2',
                    '/src/Etape2/Ex1',
                    '/src/Etape3/Ex1',
                    '/',
                    '/Travail 4',
                    '/',
                    '/Partie01',
                    '/Partie02',
                    '/Partie03',
                    '/Semaine 5',
                    '/Semaine 5/TP02/Etape 4',
                    '/Semaine 5/TP02a/Etape 5',
                    '/Semaine 5/TP02/Etape 5/Exercice 1',
                    '/Semaine 5/TP02/Etape 6',
                    ]
    find_exercise(ex_path_list, ex_repo_list, ex_dir_list, '/', '/Sem2/TP02/main.java')
    find_exercise(ex_path_list, ex_repo_list, ex_dir_list, '/Semaine 3/TP 2', '/src/Etape2/main.java')
    find_exercise(ex_path_list, ex_repo_list, ex_dir_list, '/Semaine 3', '/src/TravailPratique02/Etape2/main.java')
    pass

if __name__=="__main__":
    test_session()
    test_course()
    test_group()
    test_student()
    test_exercisePath()
