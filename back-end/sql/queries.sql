--User creation
CREATE USER admin;

--Database creation
CREATE DATABASE quest_store WITH OWNER = admin;
GRANT ALL PRIVILEGES ON quest_store TO admin;

--Connection to new created database
\c quest_store admin

--Tables creation
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
	user_type TEXT REFERENCES user_type(user_type_id),
	status TEXT REFERENCES user_status(user_status_id)
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
IF NOT EXISTS user_type (
    user_type_id SERIAL PRIMARY KEY,
    user_type_name TEXT
);


CREATE TABLE
IF NOT EXISTS user_status (
    user_status_id SERIAL PRIMARY KEY,
    user_status_name TEXT
);

--Default data insertion
INSERT INTO login_data (login, password)
VALUES ('admin', 'admin');

INSERT INTO access_level (level_id, min_lifetime_coins, max_lifetime_coins)
VALUES (1, 0, 50), (2, 51, 150), (3, 151, 250), (4, 251, 350), (5, 351, null); 
