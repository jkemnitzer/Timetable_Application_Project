DROP TABLE IF EXISTS rooms;

CREATE TABLE rooms
(
    id       INT PRIMARY KEY,
    number   VARCHAR(200),
    building VARCHAR(200)
);

INSERT INTO rooms (id, number, building)
VALUES (1, 'B109', 'B');
INSERT INTO rooms (id, number, building)
VALUES (2, 'G028/29', 'G');

-- feature lectures and lessons
DROP TABLE IF EXISTS lectures;
DROP TABLE IF EXISTS lessons;

CREATE SEQUENCE IF NOT EXISTS lecture_seq;
CREATE SEQUENCE IF NOT EXISTS lesson_seq;

CREATE TABLE lectures
(
    id   BIGINT DEFAULT lectures_seq.nextval PRIMARY KEY,
    lectureName VARCHAR(200)
);

CREATE TABLE lessons
(
    id               BIGINT DEFAULT lesson_seq.nextval PRIMARY KEY,
    lecture_id       BIGINT,
    lessonName       VARCHAR(50),
    prof             VARCHAR(50)
    roomRequirement  VARCHAR(50)

    FOREIGN KEY (lecture_id) REFERENCES lecture (id)
);
INSERT INTO lectures (id, lectureName)
VALUES ('OOP1');
INSERT INTO lessons (lecture_id, lessonName, prof,  roomRequirement)
VALUES (1, 'Vorlesung', 'Prof. Atzenbeck', 'Beamer');
INSERT INTO lessons (lecture_id, lessonName, prof,  roomRequirement)
VALUES (1, 'Übung', 'Prof. Peinel', 'PC Raum');


-- feature Study Programs

DROP TABLE IF EXISTS t_semester;
DROP TABLE IF EXISTS t_program;

CREATE SEQUENCE IF NOT EXISTS t_semester_seq;
CREATE SEQUENCE IF NOT EXISTS t_program_seq;

CREATE TABLE t_program
(
    id   BIGINT DEFAULT t_program_seq.nextval PRIMARY KEY,
    name VARCHAR(200)
);

CREATE TABLE t_semester
(
    id               BIGINT DEFAULT t_semester_seq.nextval PRIMARY KEY,
    program_id       BIGINT NOT NULL,
    number           VARCHAR(50),
    exp_participants INT,
    act_participants INT,
    FOREIGN KEY (program_id) REFERENCES t_program (id)
);

INSERT INTO t_program (name)
VALUES ('Master Informatik');
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (1, '1u2', 10, 11);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (1, '3', 5, 5);

INSERT INTO t_program (name)
VALUES ('Informatik');
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (2, '1', 40, 38);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (2, '2', 40, 38);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (2, '3', 35, 35);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (2, '4', 35, 31);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (2, '5', 35, 26);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (2, '6', 30, 26);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (2, '7', 30, 22);

INSERT INTO t_program (name)
VALUES ('Medien Informatik');
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (3, '1', 40, 38);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (3, '2', 40, 38);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (3, '3', 35, 35);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (3, '4', 35, 31);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (3, '5', 35, 26);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (3, '6', 30, 26);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (3, '7', 30, 22);

-- feature user

DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;
DROP TABLE IF EXISTS t_user_role_map;

CREATE SEQUENCE IF NOT EXISTS t_user_seq;
CREATE SEQUENCE IF NOT EXISTS t_role_seq;

CREATE TABLE t_user
(
    id            BIGINT DEFAULT t_user_seq.nextval PRIMARY KEY,
    username      VARCHAR(100),
    password      VARCHAR(100),
    password_salt VARCHAR(100),
    email         VARCHAR(100),
    created       TIMESTAMP,
    last_updated  TIMESTAMP,
    status        VARCHAR(100)
);

CREATE TABLE t_role
(
    id   BIGINT DEFAULT t_role_seq.nextval PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE t_user_role_map
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES t_user (id),
    FOREIGN KEY (role_id) REFERENCES t_role (id)
);

INSERT INTO t_role (name)
VALUES ('ADMIN');
INSERT INTO t_role (name)
VALUES ('STUDENT');

INSERT INTO t_user (username, password, password_salt, email, created, last_updated, status)
VALUES ('BMars', '', '', 'better.mars@hof-university.de', now(), now(), 'ACTIVE');
INSERT INTO t_user (username, password, password_salt, email, created, last_updated, status)
VALUES ('CJupiter', '', '', 'caeser.jupiter@hof-university.de', now(), now(), 'ACTIVE');
INSERT INTO t_user (username, password, password_salt, email, created, last_updated, status)
VALUES ('SPluto', '', '', 'sad.pluto@hof-university.de', now(), now(), 'INACTIVE');

INSERT INTO t_user_role_map (user_id, role_id)
values (1, 1);
INSERT INTO t_user_role_map (user_id, role_id)
values (2, 2);
INSERT INTO t_user_role_map (user_id, role_id)
values (3, 1);
INSERT INTO t_user_role_map (user_id, role_id)
values (3, 2);

