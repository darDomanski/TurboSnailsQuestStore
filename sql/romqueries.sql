--Database reset
DROP DATABASE quest_store;
DROP USER admin;

--User creation
CREATE USER admin WITH ENCRYPTED PASSWORD 'admin';

--Database creation
CREATE DATABASE quest_store WITH OWNER = admin;
GRANT CONNECT ON DATABASE quest_store TO admin;
--GRANT USAGE ON SCHEMA public to admin;
--GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
--GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin;

--Connection to new created database
\c quest_store admin localhost

--Tables creation
CREATE TABLE
IF NOT EXISTS user_type (
    user_type_id SERIAL PRIMARY KEY,
    user_type_name TEXT
);

CREATE TABLE
IF NOT EXISTS user_status (
    user_status_id SERIAL PRIMARY KEY,
    user_status_name TEXT
);

CREATE TABLE
IF NOT EXISTS access_level (
	level_id INTEGER PRIMARY KEY,
	min_lifetime_coins INTEGER,
	max_lifetime_coins INTEGER
);

CREATE TABLE
IF NOT EXISTS quest (
	id SERIAL PRIMARY KEY,
	access_level INTEGER REFERENCES access_level(level_id),
	title TEXT,
	description TEXT,
	quest_value INTEGER,
	quest_type VARCHAR(5)
);

CREATE TABLE
IF NOT EXISTS class_ (
	class_id SERIAL PRIMARY KEY,
	name TEXT
);


CREATE TABLE
IF NOT EXISTS qs_user (
	id SERIAL PRIMARY KEY,
	first_name TEXT,
	last_name TEXT,
	email VARCHAR(320),
	class_id INTEGER REFERENCES class_(class_id),
	user_type INTEGER REFERENCES user_type(user_type_id),
	status INTEGER REFERENCES user_status(user_status_id)
);

CREATE TABLE
IF NOT EXISTS login_data  (
	id INTEGER REFERENCES qs_user(id),
	login TEXT,
	password TEXT
);

CREATE TABLE
IF NOT EXISTS artifact (
	id SERIAL PRIMARY KEY,
	access_level INTEGER REFERENCES access_level(level_id),
	title TEXT,
	description TEXT,
	artifact_price INTEGER,
	artifact_type VARCHAR(5)
);

--CREATE TABLE
--IF NOT EXISTS mentor (
--	id INTEGER REFERENCES login_data (id),
--	first_name TEXT,
--	last_name TEXT,
--	email VARCHAR(320),
--	class_id INTEGER REFERENCES class_(class_id)
--);
--
--CREATE TABLE
--IF NOT EXISTS student (
--	id INTEGER REFERENCES login_data (id),
--	first_name TEXT,
--	last_name TEXT,
--	email VARCHAR(320),
--	class_id INTEGER REFERENCES class_(class_id)
--);

CREATE TABLE
IF NOT EXISTS student_quest (
	student_id INTEGER REFERENCES qs_user(id),
	quest_id INTEGER REFERENCES quest(id)
);

CREATE TABLE
IF NOT EXISTS student_artifact (
	student_id INTEGER REFERENCES qs_user(id),
	artifact_id INTEGER REFERENCES artifact(id)
);

CREATE TABLE
IF NOT EXISTS crowdfunding (
	artifact_id INTEGER REFERENCES artifact(id),
	money_collected INTEGER
);

CREATE TABLE
IF NOT EXISTS wallet (
	student_id INTEGER REFERENCES qs_user(id),
	current_coins INTEGER,
	lifetime_coins INTEGER
);


--Default data insertion
INSERT INTO login_data (login, password)
VALUES ('admin', 'admin');

INSERT INTO access_level (level_id, min_lifetime_coins, max_lifetime_coins)
VALUES (1, 0, 50), (2, 51, 150), (3, 151, 250), (4, 251, 350), (5, 351, null);

INSERT INTO class_ (name)
VALUES ('progbasic'), ('java'), ('web'), ('advanced');

INSERT INTO user_type (user_type_name)
VALUES ('mentor'), ('student');

INSERT INTO user_status (user_status_name)
VALUES ('active'), ('inactve');

INSERT INTO quest
  (id, access_level, title, description, quest_value, quest_type)
  VALUES
  (1,2,'Exploring a dungeon','Finishing a Teamwork week',50,'basic'),
  (2,2,'Solving the magic puzzle','Finishing an SI assignment',100,'basic'),
  (3,2,'Exploring a dungeon','Finishing a Teamwork week',50,'basic'),
  (4,2,'Solving the magic puzzle','Finishing an SI assignment',100,'basic'),
  (5,2,'Exploring a dungeon','Finishing a Teamwork week',50,'extra'),
  (6,2,'Solving the magic puzzle','Finishing an SI assignment',100,'extra');

INSERT INTO artifact
  (id, access_level, title, description, artifact_price, artifact_type )
  VALUES
  (1,2,'Combat training','Private mentoring',50,'basic'),
  (2,2,'Sanctuary','You can spend a day in home office',100,'basic'),
  (3,2,'Time Travel','extend SI week assignment deadline by one day',50,'basic'),
  (4,3,'Circle of Sorcery',' 60 min workshop by a mentor(s) of the chosen topic',100,'basic'),
  (5,4,'Summon Code Elemental','Mentor joins a student team for a one hour',50,'extra'),
  (6,3,'Solving the magic puzzle','Finishing an SI assignment',100,'extra');