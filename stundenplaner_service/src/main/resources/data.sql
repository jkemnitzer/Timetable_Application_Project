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

INSERT INTO t_program (name) VALUES ('Master Informatik');
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (1, '1u2', 10, 11);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (1, '3', 5, 5);

INSERT INTO t_program (name) VALUES ('Informatik');
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (2, '1', 40, 38);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (2, '2', 40, 38);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (2, '3', 35, 35);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (2, '4', 35, 31);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (2, '5', 35, 26);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (2, '6', 30, 26);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (2, '7', 30, 22);

INSERT INTO t_program (name) VALUES ('Medien Informatik');
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (3, '1', 40, 38);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (3, '2', 40, 38);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (3, '3', 35, 35);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (3, '4', 35, 31);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (3, '5', 35, 26);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (3, '6', 30, 26);
INSERT INTO t_semester (program_id, number, exp_participants, act_participants) VALUES (3, '7', 30, 22);