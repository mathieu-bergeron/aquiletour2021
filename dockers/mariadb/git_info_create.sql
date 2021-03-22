CREATE DATABASE IF NOT EXISTS git_info;
USE git_info;

# Table DEPOTS:
# (URL Depot) - Session - Cours - Groupe - Etudiant - TP
CREATE TABLE IF NOT EXISTS depot (
	url_depot VARCHAR(255) PRIMARY KEY NOT NULL,
	session CHAR(5) NOT NULL,
	cours CHAR(7) NOT NULL,
	groupe TINYINT,
	etudiant_id INT NOT NULL,
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
