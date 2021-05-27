CREATE DATABASE IF NOT EXISTS git_info;
USE git_info;

# Session : "H2021"
# Cours : "mathieu.bergeron/StruDon"
# Groupe : "01"
# CalPath : "/TP1/Exercice 1"
# DepotPath : "/TP1"
# FilePath : "/exercice01"
# CompletionKW : "Exercice 1"
# URLDepot : "https://github.com/test/test.git"
# Etudiant : "bob.berancourt"
# CommitID : "asdfasdfw34qfadsfasdrf3"
# Date : "1615215942"
# ShortMessage : "Blah Blah"
# Message : "Blah Blah Blah Blah"
# FilePath : "/src/atelier2/Exercise3.java"
# Insert : "5"
# Delete : "10"
# Effort : "34"

# Table EXERCICE:
# (Session - Cours - Groupe - CalPath) - DepotPath - FilePath - CompletionKW
CREATE TABLE IF NOT EXISTS exercise (
	session_id CHAR(5) NOT NULL CHECK (session_id RLIKE '^[A-Z][0-9][0-9][0-9][0-9]$'),
	course_id VARCHAR(127) NOT NULL CHECK (course_id RLIKE '^[a-z0-9.-]+/[a-z0-9_.-]+$'),
	group_id CHAR(2) NOT NULL CHECK (group_id RLIKE '^[0-9][0-9]$'),
	exercise_path VARCHAR(255) NOT NULL,
	repo_path VARCHAR(255) NOT NULL,
	file_path VARCHAR(255) NOT NULL,
	completion_kw VARCHAR(127),
	PRIMARY KEY (session_id, course_id, group_id, exercise_path));

# Table DEPOT:
# (URL Depot) - Session - Cours - Groupe - Etudiant - DepotPath
CREATE TABLE IF NOT EXISTS repository (
	repo_url VARCHAR(255) PRIMARY KEY NOT NULL,
	repo_host CHAR(2) NOT NULL CHECK (repo_host RLIKE '^[A-Z][A-Z]$'),
	session_id CHAR(5) NOT NULL CHECK (session_id RLIKE '^[A-Z][0-9][0-9][0-9][0-9]$'),
	course_id VARCHAR(127) NOT NULL CHECK (course_id RLIKE '^[a-z0-9.-]+/[a-z0-9_.-]+$'),
	group_id CHAR(2) NOT NULL CHECK (group_id RLIKE '^[0-9][0-9]$'),
	student VARCHAR(127) NOT NULL CHECK (student RLIKE '^[a-z0-9.-]+$'),
	repo_path VARCHAR(255) NOT NULL
#	FOREIGN KEY (session_id, course_id, group_id, repo_path) REFERENCES exercise(session_id, course_id, group_id, repo_path)
# 	TODO: Trigger to validate data
	);

# Table COMMIT:
# (URL Depot - Commit ID) - Date - ShortMessage - Message - CalPath
CREATE TABLE IF NOT EXISTS commit (
	repo_url VARCHAR(255) NOT NULL,
	commit_id CHAR(40) NOT NULL CHECK (commit_id RLIKE '^[a-f0-9]{40}$'),
	commit_date DATETIME NOT NULL,
	summary VARCHAR(255),
	message TEXT,
	exercise_path VARCHAR(255),
	PRIMARY KEY (repo_url, commit_id),
	FOREIGN KEY (repo_url) REFERENCES repository(repo_url)
#	FOREIGN KEY (exercise_path) REFERENCES exercise(exercise_path)
# 	TODO: Trigger to validate data
	);

# Table FICHIER:
# (URL Depot - Commit ID - FilePath) - Insert - Delete - Effort - CalPath
CREATE TABLE IF NOT EXISTS commit_file (
	repo_url VARCHAR(255) NOT NULL,
	commit_id CHAR(40) NOT NULL CHECK (commit_id RLIKE '^[a-f0-9]{40}$'),
	file_path VARCHAR(255) NOT NULL,
	insert_count INT NOT NULL,
	delete_count INT NOT NULL,
	effort INT NOT NULL,
	exercise_path VARCHAR(255) NOT NULL,
	PRIMARY KEY (repo_url, commit_id, file_path),
	FOREIGN KEY (repo_url, commit_id) REFERENCES commit(repo_url, commit_id)
#	FOREIGN KEY (exercise_path) REFERENCES exercise(exercise_path)
# 	TODO: Trigger to validate data
	);

