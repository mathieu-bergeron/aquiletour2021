CREATE DATABASE IF NOT EXISTS git_info;
USE git_info;

# Table DEPOTS:
# (URL Depot) - Session - Cours - Groupe - Etudiant - TP
CREATE TABLE IF NOT EXISTS depot (
	url_depot VARCHAR(255) PRIMARY KEY NOT NULL,
	depot_host CHAR(2) NOT NULL CHECK (depot_host RLIKE '^[A-Z][A-Z]$'),
	session CHAR(3) NOT NULL CHECK (session RLIKE '^[A-Z][0-9][0-9]$'),
	cours CHAR(7) NOT NULL CHECK (cours RLIKE '^[0-9][0-9][0-9]-[A-Z0-9][A-Z0-9][A-Z0-9]$'),
	groupe CHAR(2) NOT NULL CHECK (groupe RLIKE '^[0-9][0-9]$'),
	etudiant_id CHAR(7) NOT NULL CHECK (etudiant_id RLIKE '^[0-9][0-9][0-9][0-9][0-9][0-9][0-9]$'),
	section VARCHAR(255));

# Table COMMIT:
# (URL Depot - Commit ID) - Date - Message
CREATE TABLE IF NOT EXISTS commit (
	url_depot VARCHAR(255) NOT NULL,
	commit_id CHAR(40) NOT NULL,
	commit_date DATETIME NOT NULL,
	message TEXT,
	PRIMARY KEY (url_depot, commit_id),
	FOREIGN KEY (url_depot) REFERENCES depot(url_depot));

# Table FICHIER:
# (URL Depot - Commit ID - Fichier Path) - Insert - Delete
CREATE TABLE IF NOT EXISTS element (
	url_depot VARCHAR(255) NOT NULL,
	commit_id CHAR(40) NOT NULL,
	file_path VARCHAR(255) NOT NULL,
	insert_count INT,
	delete_count INT,
	PRIMARY KEY (url_depot, commit_id, file_path),
	FOREIGN KEY (url_depot, commit_id) REFERENCES commit(url_depot, commit_id));

# Table TAGS:
# (URL Depot - Commit ID - Tag) - File(O/N) - Message(O/N)
CREATE TABLE IF NOT EXISTS category (
	url_depot VARCHAR(255) NOT NULL,
	commit_id CHAR(40) NOT NULL,
	category VARCHAR(255) NOT NULL,
	from_file BOOLEAN,
	from_message BOOLEAN,
	PRIMARY KEY (url_depot, commit_id, category),
	FOREIGN KEY (url_depot, commit_id) REFERENCES commit(url_depot, commit_id));
