DROP TABLE IF EXISTS t_faculty;

CREATE SEQUENCE IF NOT EXISTS t_faculty_seq;

CREATE TABLE t_faculty
(
    id   BIGINT DEFAULT t_faculty_seq.nextval PRIMARY KEY,
    name VARCHAR(200)
);

INSERT INTO t_faculty (name)
VALUES ('Informatik');
INSERT INTO t_faculty (name)
VALUES ('Wirtschaft');
INSERT INTO t_faculty (name)
VALUES ('Ingenieur');



DROP TABLE IF EXISTS t_room;

CREATE SEQUENCE IF NOT EXISTS t_room_seq;

CREATE TABLE t_room
(
    id       BIGINT DEFAULT t_room_seq.nextval PRIMARY KEY,
    number   VARCHAR(200),
    building VARCHAR(200)
);

INSERT INTO t_room (number, building)
VALUES ('B109', 'B');
INSERT INTO t_room (number, building)
VALUES ('G028/29', 'G');
INSERT INTO t_room (number, building)
VALUES ('G011', 'G');
INSERT INTO t_room (number, building)
VALUES ('Ex_Extern', 'Einstein1');
INSERT INTO t_room (number, building)
VALUES ('B006', 'B');
INSERT INTO t_room (number, building)
VALUES ('A109', 'A');

-- programs

-- feature Study Programs

DROP TABLE IF EXISTS t_semester;
DROP TABLE IF EXISTS t_program;

CREATE SEQUENCE IF NOT EXISTS t_semester_seq;
CREATE SEQUENCE IF NOT EXISTS t_program_seq;

CREATE TABLE t_program
(
    id   BIGINT DEFAULT t_program_seq.nextval PRIMARY KEY,
    name VARCHAR(200),
    fk_faculty_id BIGINT,
    FOREIGN KEY (fk_faculty_id) REFERENCES t_faculty (id)
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

INSERT INTO t_program (name, fk_faculty_id)
VALUES ('Master Informatik', 1);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (1, '1u2', 10, 11);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants)
VALUES (1, '3', 5, 5);

INSERT INTO t_program (name, fk_faculty_id)
VALUES ('Informatik',1);
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

INSERT INTO t_program (name, fk_faculty_id)
VALUES ('Medien Informatik', 1);
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

-- feature user, role, permission

DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;
DROP TABLE IF EXISTS t_user_role_map;
DROP TABLE IF EXISTS t_permission;

CREATE SEQUENCE IF NOT EXISTS t_user_seq;
CREATE SEQUENCE IF NOT EXISTS t_role_seq;
CREATE SEQUENCE IF NOT EXISTS t_permission_seq;

CREATE TABLE t_user
(
    id            BIGINT    DEFAULT t_user_seq.nextval PRIMARY KEY,
    username      VARCHAR(100),
    title         VARCHAR(200),
    first_name    VARCHAR(200),
    last_name     VARCHAR(200),
    password      VARCHAR(100),
    password_salt VARCHAR(100),
    email         VARCHAR(100),
    created       TIMESTAMP DEFAULT NOW(),
    last_updated  TIMESTAMP DEFAULT NOW(),
    status        VARCHAR(100)
);

CREATE TABLE t_role
(
    id   BIGINT DEFAULT t_role_seq.nextval PRIMARY KEY,
    type VARCHAR(100)
);

CREATE TABLE t_user_role_map
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES t_user (id),
    FOREIGN KEY (role_id) REFERENCES t_role (id)
);

CREATE TABLE t_permission
(
    id   BIGINT DEFAULT t_permission_seq.nextval PRIMARY KEY,
    type VARCHAR(100)
);

CREATE TABLE t_role_permission_map
(
    role_id       BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES t_role (id),
    FOREIGN KEY (permission_id) REFERENCES t_permission (id)
);

INSERT INTO t_role (type)
VALUES ('ADMIN');
INSERT INTO t_role (type)
VALUES ('PROFESSOR');
INSERT INTO t_role (type)
VALUES ('LECTURER');
INSERT INTO t_role (type)
VALUES ('STUDENT');
INSERT INTO t_role (type)
VALUES ('GUEST');

INSERT INTO t_permission (type)
VALUES ('CAN_READ_DELEGATED_LECTURERS');

INSERT INTO t_permission (type)
VALUES ('CAN_READ_FACULTIES');

INSERT INTO t_permission (type)
VALUES ('CAN_CREATE_LECTURES');
INSERT INTO t_permission (type)
VALUES ('CAN_READ_LECTURES');
INSERT INTO t_permission (type)
VALUES ('CAN_UPDATE_LECTURES');
INSERT INTO t_permission (type)
VALUES ('CAN_DELETE_LECTURES');

INSERT INTO t_permission (type)
VALUES ('CAN_READ_LECTURER_PROFILES');

INSERT INTO t_permission (type)
VALUES ('CAN_READ_MODULE_PREFERENCES');

INSERT INTO t_permission (type)
VALUES ('CAN_CREATE_PROGRAMS');
INSERT INTO t_permission (type)
VALUES ('CAN_READ_PROGRAMS');
INSERT INTO t_permission (type)
VALUES ('CAN_UPDATE_PROGRAMS');
INSERT INTO t_permission (type)
VALUES ('CAN_DELETE_PROGRAMS');

INSERT INTO t_permission (type)
VALUES ('CAN_READ_ROLES');

INSERT INTO t_permission (type)
VALUES ('CAN_CREATE_ROOMS');
INSERT INTO t_permission (type)
VALUES ('CAN_READ_ROOMS');
INSERT INTO t_permission (type)
VALUES ('CAN_UPDATE_ROOMS');
INSERT INTO t_permission (type)
VALUES ('CAN_DELETE_ROOMS');

INSERT INTO t_permission (type)
VALUES ('CAN_CREATE_SEMESTERS');
INSERT INTO t_permission (type)
VALUES ('CAN_READ_SEMESTERS');
INSERT INTO t_permission (type)
VALUES ('CAN_UPDATE_SEMESTERS');
INSERT INTO t_permission (type)
VALUES ('CAN_DELETE_SEMESTERS');

INSERT INTO t_permission (type)
VALUES ('CAN_READ_TIME_SLOTS');

INSERT INTO t_permission (type)
VALUES ('CAN_READ_TIME_SLOT_PREFERENCES');

INSERT INTO t_permission (type)
VALUES ('CAN_CREATE_TIME_TABLES');
INSERT INTO t_permission (type)
VALUES ('CAN_READ_TIME_TABLES');
INSERT INTO t_permission (type)
VALUES ('CAN_DELETE_TIME_TABLES');

INSERT INTO t_permission (type)
VALUES ('CAN_CREATE_LESSONS');
INSERT INTO t_permission (type)
VALUES ('CAN_READ_LESSONS');
INSERT INTO t_permission (type)
VALUES ('CAN_UPDATE_LESSONS');
INSERT INTO t_permission (type)
VALUES ('CAN_DELETE_LESSONS');

INSERT INTO t_permission (type)
VALUES ('CAN_CREATE_USERS');
INSERT INTO t_permission (type)
VALUES ('CAN_READ_USERS');
INSERT INTO t_permission (type)
VALUES ('CAN_UPDATE_USERS');
INSERT INTO t_permission (type)
VALUES ('CAN_DELETE_USERS');


INSERT INTO t_user (username, password, password_salt, email, created, last_updated, status)
VALUES ('BMars', '', '', 'better.mars@hof-university.de', NOW(), NOW(), 'ACTIVE');
INSERT INTO t_user (username, password, password_salt, email, created, last_updated, status)
VALUES ('CJupiter', '', '', 'caeser.jupiter@hof-university.de', NOW(), NOW(), 'ACTIVE');
INSERT INTO t_user (username, password, password_salt, email, created, last_updated, status)
VALUES ('SPluto', '', '', 'sad.pluto@hof-university.de', NOW(), NOW(), 'INACTIVE');
INSERT INTO t_user (username, title, first_name, last_name, email, status)
VALUES ('gkoehler', 'Prof. Dr.', 'Günther', 'Köhler', 'guenther.koehler@hof-university.de', 'GENERATED');
INSERT INTO t_user (username, title, first_name, last_name, email, status)
VALUES ('wlehmann', 'Pfarrer', 'Wolfram', 'Lehmann', 'wolfram.lehmann@hof-university.de', 'GENERATED');
INSERT INTO t_user (username, title, first_name, last_name, email, status)
VALUES ('bweber', 'Prof. Dr.', 'Beatrix', 'Weber', 'beatrix.weber@hof-university.de', 'GENERATED');
INSERT INTO t_user (username, title, first_name, last_name, email, status)
VALUES ('mseidel', 'Prof. Dr.', 'Michael', 'Seidel', 'michael.seidel@hof-university.de', 'GENERATED');
INSERT INTO t_user (username, title, first_name, last_name, email, status)
VALUES ('jscheidt', 'Prof. Dr.', 'Jörg', 'Scheidt', 'joerg.scheidt@hof-university.de', 'GENERATED');
INSERT INTO t_user (username, title, first_name, last_name, email, status)
VALUES ('cgroth', 'Prof. Dr.', 'Christian', 'Groth', 'christian.groth@hof-university.de', 'GENERATED');


INSERT INTO t_user_role_map (user_id, role_id)
VALUES (1, 1);
INSERT INTO t_user_role_map (user_id, role_id)
VALUES (2, 2);
INSERT INTO t_user_role_map (user_id, role_id)
VALUES (3, 1);
INSERT INTO t_user_role_map (user_id, role_id)
VALUES (3, 2);

INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 1);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 2);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 3);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 4);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 5);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 6);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 7);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 8);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 9);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 10);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 11);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 12);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 13);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 14);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 15);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 16);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 17);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 18);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 19);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 20);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 21);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 22);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 23);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 24);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 25);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 26);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 27);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 28);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 29);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 30);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 31);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 32);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 33);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (1, 34);

INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (5, 31);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (5, 32);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (5, 33);
INSERT INTO t_role_permission_map (role_id, permission_id)
VALUES (5, 34);
