import re
import os
import Levenshtein as le

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
    if tuple1[0] is not None and tuple2[0] is not None:
        edits = le.editops(tuple1[0], tuple2[0])
        edits_counts = {}
        for edit,_,_ in edits:
            edits_counts[edit] = edits_counts.get(edit, 0) + 1
        inserts = edits_counts.get('insert',0) + edits_counts.get('replace',0)
        deletes = edits_counts.get('delete',0) + edits_counts.get('replace',0)
    # if only insert and max 1 delete or replace
    # OR if only delete with max 1 insert or replace
        if inserts <= 1 or deletes <= 1: 
            match = True
    else:
        match = True
    if tuple1[1] != tuple2[1]:
        match = False
    return match

def match_path(file_path_ex, file_path_commit):
    # Clean exercise and commit file path
    tuples_exercise = clean_path(file_path_ex)
    tuples_commit = clean_path(file_path_commit)
    nb_match = 0
    # TODO: Make sure that the matching tuples are in the same order in the two paths
    # longest increasing subsequence
    for ex_tuple in tuples_exercise:
        match = False
        for c_tuple in tuples_commit:
            match |= match_tuples(ex_tuple, c_tuple)
        if match:
            nb_match += 1
    return nb_match, len(tuples_exercise)
    # max(len(tuples_exercise), len(tuples_commit))

def find_exercise_from_path(exercicePath_list, repo_path_list_ex, file_path_list_ex, repo_path_commit, file_path_commit):
    matched_exPath_list = []
    max_match_value = 0
    min_match_len = 99
    exact_repo = False
    if repo_path_commit in repo_path_list_ex:
        exact_repo = True
    for exPath, repoPath, filePath in zip(exercicePath_list,repo_path_list_ex, file_path_list_ex):
        if exact_repo and repo_path_commit == repoPath:
            pass
        # fix the commit path or exercise path to include part of repo path
        elif not exact_repo and repoPath.startswith(repo_path_commit):
                filePath = str(os.path.join(repoPath.split(repo_path_commit,1)[1], filePath))
        elif not exact_repo and repo_path_commit.startswith(repoPath):
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
    if len(matched_exPath_list) != 1:
        matched_exPath_list = ['/']
    return matched_exPath_list[0]

def find_exercise_from_keyword(exercicePath_list, keyword_list, commit_summary):
    matched_ex = []
    for exPath, keyword in zip(exercicePath_list, keyword_list):
        if keyword is not None and len(keyword) > 0 and 0 <= commit_summary.lower().find(keyword.lower()) < 10:
            matched_ex.append(exPath)
    if len(matched_ex) != 1:
        matched_ex = [None]
    return matched_ex[0]


# Some Unit Test
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
    find_exercise_from_path(ex_path_list, ex_repo_list, ex_dir_list, '/', '/Sem2/TP02/main.java')
    find_exercise_from_path(ex_path_list, ex_repo_list, ex_dir_list, '/Semaine 3/TP 2', '/src/Etape2/main.java')
    find_exercise_from_path(ex_path_list, ex_repo_list, ex_dir_list, '/Semaine 3', '/src/TravailPratique02/Etape2/main.java')

def test_kwMatch():
    ex_path = ['/Semaine 1/TravailPratique 1',
                '/Semaine 1/TravailPratique 2',
                '/Semaine 2/TravailPratique 3',
                '/Semaine 3/Exercice01',
                '/Semaine 3/Exercice02',
                '/Semaine 3/Exercice05',
                '/Semaine 7/Exercice06',
                '/Semaine 8/Exercice07',
                '/Semaine 5/Examen/Intra']
    kw = ['TP 1','TP 2','TP 3','Exercice 1','Exercice 2', None, '', 'Ex06','Examen']
    print(find_exercise_from_keyword(ex_path,kw,'Test de keyword'))
    print(find_exercise_from_keyword(ex_path,kw,'TP 2: Test de keyword'))
    print(find_exercise_from_keyword(ex_path,kw,'[ Exercice 1 ] Test de keyword'))

if __name__=="__main__":
    test_exercisePath()
    test_kwMatch()