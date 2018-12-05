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
	aritfact_price INTEGER,
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

CREATE TABLE
IF NOT EXISTS session (
user_id INTEGER,
session_id TEXT UNIQUE
);

--Default data insertion

-- Create levels, classes, user types and user statuses
INSERT INTO access_level (level_id, min_lifetime_coins, max_lifetime_coins)
VALUES (1, 0, 50), (2, 51, 150), (3, 151, 250), (4, 251, 350), (5, 351, null); 

INSERT INTO class_ (name)
VALUES ('progbasic'), ('java'), ('web'), ('advanced');

INSERT INTO user_type (user_type_name)
VALUES ('mentor'), ('student');

INSERT INTO user_status (user_status_name)
VALUES ('active'), ('inactve');

-- Create users

-- Mentors
INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status)
VALUES ('Marek', 'Grzybek', 'marco.funghi@codecool.com', (SELECT class_id FROM class_ WHERE name='progbasic'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='mentor'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));
INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status)
VALUES ('Agnieszka', 'Koszany', 'agi.koszany@codecool.com', (SELECT class_id FROM class_ WHERE name='java'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='mentor'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));
INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status)
VALUES ('Mateusz', 'Ostafil', 'ojciec.mateusz@codecool.com', (SELECT class_id FROM class_ WHERE name='web'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='mentor'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));
INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status)
VALUES ('Wojtek', 'Makieła', 'woy.tech@codecool.com', (SELECT class_id FROM class_ WHERE name='advanced'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='mentor'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));
-- Students
INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status)
VALUES ('Zbyszek', 'Kieliszek', 'zbych.kielon@codecool.com', (SELECT class_id FROM class_ WHERE name='progbasic'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));
INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status)
VALUES ('Marianna', 'Szklanka', 'glass.marie@codecool.com', (SELECT class_id FROM class_ WHERE name='progbasic'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));
INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status)
VALUES ('Janek', 'Wiśniewski', 'johnny.wisnia@codecool.com', (SELECT class_id FROM class_ WHERE name='java'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));
INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status)
VALUES ('Frank', 'Sinatra', 'frank@codecool.com', (SELECT class_id FROM class_ WHERE name='java'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));
INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status)
VALUES ('Karyna', 'Pałka', 'kari.palka@codecool.com', (SELECT class_id FROM class_ WHERE name='web'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));
INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status)
VALUES ('Sebastian', 'Wałek', 'seba.rollo@codecool.com', (SELECT class_id FROM class_ WHERE name='web'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));
INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status)
VALUES ('Krzysztof', 'Krawczyk', 'krawczyk.superstar@codecool.com', (SELECT class_id FROM class_ WHERE name='advanced'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));
INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status)
VALUES ('Roman', 'Buliński', 'boolean@codecool.com', (SELECT class_id FROM class_ WHERE name='advanced'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));

-- Create login accounts
INSERT INTO login_data (login, password)
VALUES ('admin', 'admin');
INSERT INTO login_data (id, login, password)
VALUES ((SELECT id FROM qs_user WHERE first_name='Marek'), 'marek123', 'marek123');
INSERT INTO login_data (id, login, password)
VALUES ((SELECT id FROM qs_user WHERE first_name='Agnieszka'), 'agi123', 'agi123');
INSERT INTO login_data (id, login, password)
VALUES ((SELECT id FROM qs_user WHERE first_name='Mateusz'), 'mateusz123', 'mateusz123');
INSERT INTO login_data (id, login, password)
VALUES ((SELECT id FROM qs_user WHERE first_name='Wojtek'), 'wojtech123', 'wojtech123');
INSERT INTO login_data (id, login, password)
VALUES ((SELECT id FROM qs_user WHERE first_name='Zbyszek'), 'zbyszek123', 'zbyszek123');
INSERT INTO login_data (id, login, password)
VALUES ((SELECT id FROM qs_user WHERE first_name='Marianna'), 'marianna123', 'marianna123');
INSERT INTO login_data (id, login, password)
VALUES ((SELECT id FROM qs_user WHERE first_name='Janek'), 'janek123', 'janek123');
INSERT INTO login_data (id, login, password)
VALUES ((SELECT id FROM qs_user WHERE first_name='Frank'), 'frank123', 'frank123');
INSERT INTO login_data (id, login, password)
VALUES ((SELECT id FROM qs_user WHERE first_name='Karyna'), 'karyna123', 'karyna123');
INSERT INTO login_data (id, login, password)
VALUES ((SELECT id FROM qs_user WHERE first_name='Sebastian'), 'sebastian123', 'sebastian123');
INSERT INTO login_data (id, login, password)
VALUES ((SELECT id FROM qs_user WHERE first_name='Krzysztof'), 'krzysztof123', 'krzysztof123');
INSERT INTO login_data (id, login, password)
VALUES ((SELECT id FROM qs_user WHERE first_name='Roman'), 'roman123', 'roman123');

-- Create quests


