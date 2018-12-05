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
VALUES
('Marek', 'Grzybek', 'marco.funghi@codecool.com', (SELECT class_id FROM class_ WHERE name='progbasic'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='mentor'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active')),
('Agnieszka', 'Koszany', 'agi.koszany@codecool.com', (SELECT class_id FROM class_ WHERE name='java'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='mentor'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active')),
('Mateusz', 'Ostafil', 'ojciec.mateusz@codecool.com', (SELECT class_id FROM class_ WHERE name='web'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='mentor'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active')),
('Wojtek', 'Makieła', 'woy.tech@codecool.com', (SELECT class_id FROM class_ WHERE name='advanced'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='mentor'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active')),
('Zbyszek', 'Kieliszek', 'zbych.kielon@codecool.com', (SELECT class_id FROM class_ WHERE name='progbasic'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active')),
('Marianna', 'Szklanka', 'glass.marie@codecool.com', (SELECT class_id FROM class_ WHERE name='progbasic'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active')),
('Janek', 'Wiśniewski', 'johnny.wisnia@codecool.com', (SELECT class_id FROM class_ WHERE name='java'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active')),
('Frank', 'Sinatra', 'frank@codecool.com', (SELECT class_id FROM class_ WHERE name='java'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active')),
('Karyna', 'Pałka', 'kari.palka@codecool.com', (SELECT class_id FROM class_ WHERE name='web'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active')),
('Sebastian', 'Wałek', 'seba.rollo@codecool.com', (SELECT class_id FROM class_ WHERE name='web'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active')),
('Krzysztof', 'Krawczyk', 'krawczyk.superstar@codecool.com', (SELECT class_id FROM class_ WHERE name='advanced'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active')),
('Roman', 'Buliński', 'boolean@codecool.com', (SELECT class_id FROM class_ WHERE name='advanced'),
						(SELECT user_type_id FROM user_type WHERE user_type_name='student'),
						(SELECT user_status_id FROM user_status WHERE user_status_name='active'));

-- Create login accounts
INSERT INTO login_data (login, password)
VALUES ('admin', 'admin');
INSERT INTO login_data (id, login, password)
VALUES
((SELECT id FROM qs_user WHERE id=1), 'marek123', 'marek123'),
((SELECT id FROM qs_user WHERE id=2), 'agi123', 'agi123'),
((SELECT id FROM qs_user WHERE id=3), 'mateusz123', 'mateusz123'),
((SELECT id FROM qs_user WHERE id=4), 'wojtech123', 'wojtech123'),
((SELECT id FROM qs_user WHERE id=5), 'zbyszek123', 'zbyszek123'),
((SELECT id FROM qs_user WHERE id=6), 'marianna123', 'marianna123'),
((SELECT id FROM qs_user WHERE id=7), 'janek123', 'janek123'),
((SELECT id FROM qs_user WHERE id=8), 'frank123', 'frank123'),
((SELECT id FROM qs_user WHERE id=9), 'karyna123', 'karyna123'),
((SELECT id FROM qs_user WHERE id=10), 'sebastian123', 'sebastian123'),
((SELECT id FROM qs_user WHERE id=11), 'krzysztof123', 'krzysztof123'),
((SELECT id FROM qs_user WHERE id=12), 'roman123', 'roman123');

-- Create quests
INSERT INTO quest (access_level, title, description, quest_value, quest_type)
VALUES
(1, 'Exploring a dungeon', 'Finishing a Teamwork week', 100, 'basic'),
(1, 'Solving the magic puzzle', 'Finishing an SI assignment', 100, 'basic'),
(1, 'Slaying a dragon', 'Passing a Checkpoint in the first attempt', 500, 'magic'),
(2, 'Spot trap', 'Spot a major mistake in the assignment', 50, 'basic'),
(2, 'Taming a pet', 'Doing a demo about a pet project', 100, 'basic'),
(2, 'Recruiting some n00bs', 'Taking part in the student screening process', 100, 'magic'),
(3, 'Forging weapons', 'Organizing a workshop for other students', 400, 'basic'),
(3, 'Master the mornings', 'Attend 1 months without being late', 300, 'basic'),
(3, 'Fast as an unicorn', 'deliver 4 consecutive SI week assignments on time', 500, 'magic'),
(4, 'Achiever', 'set up a SMART goal accepted by a mentor, then achieve it', 1000, 'basic'),
(4, 'Fortune', 'students choose the best project of the week. Selected team scores', 500, 'magic'),
(5, 'Creating an enchanted scroll', 'Creating extra material for the current TW/SI topic (should be revised by mentors)', 500, 'basic'),
(5, 'Enter the arena', 'Do a presentation on a meet-up', 500, 'magic');

-- Create artifacts
INSERT INTO artifact (access_level, title, description, artifact_price, artifact_type)
VALUES
((SELECT level_id FROM access_level WHERE level_id=1), 'Combat training', 'Private mentoring', 50, 'basic'),
((SELECT level_id FROM access_level WHERE level_id=1), 'Sanctuary', 'You can spend a day in home office', 1000, 'magic'),
((SELECT level_id FROM access_level WHERE level_id=2), 'Time Travel', 'Extend SI week assignment deadline by one day', 750, 'basic'),
((SELECT level_id FROM access_level WHERE level_id=2), 'Circle of Sorcery', '60 min workshop by a mentor(s) of the chosen topic', 6000, 'magic'),
((SELECT level_id FROM access_level WHERE level_id=3), 'Summon Code Elemental', 'Mentor joins a students team for a one hour', 2500, 'basic'),
((SELECT level_id FROM access_level WHERE level_id=4), 'Tome of knowledge', 'Extra material for the current topic', 1500, 'basic'),
((SELECT level_id FROM access_level WHERE level_id=4), 'Transform mentors', 'All mentors should dress up as pirates (or just funny) for the day', 5000, 'magic'),
((SELECT level_id FROM access_level WHERE level_id=5), 'Teleport', 'The whole course goes to an off-school program instead for a day', 30000, 'magic');

-- Assign quests to students
INSERT INTO student_quest (student_id, quest_id)
VALUES
((SELECT id FROM qs_user WHERE id=5), (SELECT id FROM quest WHERE id=1)),
((SELECT id FROM qs_user WHERE id=6), (SELECT id FROM quest WHERE id=1)),
((SELECT id FROM qs_user WHERE id=7), (SELECT id FROM quest WHERE id=2)),
((SELECT id FROM qs_user WHERE id=7), (SELECT id FROM quest WHERE id=3)),
((SELECT id FROM qs_user WHERE id=8), (SELECT id FROM quest WHERE id=1)),
((SELECT id FROM qs_user WHERE id=8), (SELECT id FROM quest WHERE id=4)),
((SELECT id FROM qs_user WHERE id=8), (SELECT id FROM quest WHERE id=5)),
((SELECT id FROM qs_user WHERE id=9), (SELECT id FROM quest WHERE id=2)),
((SELECT id FROM qs_user WHERE id=9), (SELECT id FROM quest WHERE id=4)),
((SELECT id FROM qs_user WHERE id=9), (SELECT id FROM quest WHERE id=6)),
((SELECT id FROM qs_user WHERE id=10), (SELECT id FROM quest WHERE id=1)),
((SELECT id FROM qs_user WHERE id=10), (SELECT id FROM quest WHERE id=3)),
((SELECT id FROM qs_user WHERE id=10), (SELECT id FROM quest WHERE id=5)),
((SELECT id FROM qs_user WHERE id=10), (SELECT id FROM quest WHERE id=7)),
((SELECT id FROM qs_user WHERE id=11), (SELECT id FROM quest WHERE id=10)),
((SELECT id FROM qs_user WHERE id=11), (SELECT id FROM quest WHERE id=13)),
((SELECT id FROM qs_user WHERE id=12), (SELECT id FROM quest WHERE id=4)),
((SELECT id FROM qs_user WHERE id=12), (SELECT id FROM quest WHERE id=8)),
((SELECT id FROM qs_user WHERE id=12), (SELECT id FROM quest WHERE id=11)),
((SELECT id FROM qs_user WHERE id=12), (SELECT id FROM quest WHERE id=12));

-- Assign artifacts to students
INSERT INTO student_artifact (student_id, artifact_id)
VALUES
((SELECT id FROM qs_user WHERE id=7), (SELECT id FROM artifact WHERE id=1)),
((SELECT id FROM qs_user WHERE id=8), (SELECT id FROM artifact WHERE id=1)),
((SELECT id FROM qs_user WHERE id=9), (SELECT id FROM artifact WHERE id=1)),
((SELECT id FROM qs_user WHERE id=9), (SELECT id FROM artifact WHERE id=3)),
((SELECT id FROM qs_user WHERE id=11), (SELECT id FROM artifact WHERE id=2)),
((SELECT id FROM qs_user WHERE id=11), (SELECT id FROM artifact WHERE id=4)),
((SELECT id FROM qs_user WHERE id=12), (SELECT id FROM artifact WHERE id=1)),
((SELECT id FROM qs_user WHERE id=12), (SELECT id FROM artifact WHERE id=5)),
((SELECT id FROM qs_user WHERE id=12), (SELECT id FROM artifact WHERE id=7)),
((SELECT id FROM qs_user WHERE id=12), (SELECT id FROM artifact WHERE id=8));

-- Assign artifacts to students
INSERT INTO student_artifact (student_id, artifact_id)
VALUES
((SELECT id FROM qs_user WHERE id=7), (SELECT id FROM artifact WHERE id=1)),
((SELECT id FROM qs_user WHERE id=8), (SELECT id FROM artifact WHERE id=1)),
((SELECT id FROM qs_user WHERE id=9), (SELECT id FROM artifact WHERE id=1)),
((SELECT id FROM qs_user WHERE id=9), (SELECT id FROM artifact WHERE id=3)),
((SELECT id FROM qs_user WHERE id=11), (SELECT id FROM artifact WHERE id=2)),
((SELECT id FROM qs_user WHERE id=11), (SELECT id FROM artifact WHERE id=4)),
((SELECT id FROM qs_user WHERE id=12), (SELECT id FROM artifact WHERE id=1)),
((SELECT id FROM qs_user WHERE id=12), (SELECT id FROM artifact WHERE id=5)),
((SELECT id FROM qs_user WHERE id=12), (SELECT id FROM artifact WHERE id=7)),
((SELECT id FROM qs_user WHERE id=12), (SELECT id FROM artifact WHERE id=8));

-- Create croudfund artifacts
INSERT INTO crowdfunding (artifact_id, money_collected)
VALUES
((SELECT id FROM artifact WHERE id=2), 50),
((SELECT id FROM artifact WHERE id=3), 50),
((SELECT id FROM artifact WHERE id=5), 50),
((SELECT id FROM artifact WHERE id=7),50);

-- Fill students wallets
INSERT INTO wallet (student_id, current_coins, lifetime_coins)
VALUES
((SELECT id FROM qs_user WHERE id=5), 50, 100),
((SELECT id FROM qs_user WHERE id=6), 300, 150),
((SELECT id FROM qs_user WHERE id=7), 100, 50),
((SELECT id FROM qs_user WHERE id=8), 300, 0),
((SELECT id FROM qs_user WHERE id=9), 800, 1200),
((SELECT id FROM qs_user WHERE id=10), 500, 3000),
((SELECT id FROM qs_user WHERE id=11), 7000, 1000),
((SELECT id FROM qs_user WHERE id=12), 30000, 30000);
