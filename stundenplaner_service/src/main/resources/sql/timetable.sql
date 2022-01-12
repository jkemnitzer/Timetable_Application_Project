-- timetable
-- temp

DROP TABLE IF EXISTS t_lecture;

CREATE SEQUENCE t_lecture_seq;

CREATE TABLE t_lecture
(
    id       BIGINT     DEFAULT t_lecture_seq.nextval PRIMARY KEY,
    language VARCHAR(2) DEFAULT 'DE',
    name     VARCHAR(200)
);

INSERT INTO t_lecture (name)
VALUES ('Aktuelle Themen zur IT-Sicherheit');
INSERT INTO t_lecture (name)
VALUES ('Einführung in Management, Organisation und Leadership');
INSERT INTO t_lecture (name)
VALUES ('IT- und Datenschutzrecht');
INSERT INTO t_lecture (name, language)
VALUES ('Development and Design of Business Models', 'EN');
INSERT INTO t_lecture (name, language)
VALUES ('Data Mining and Machine Learning', 'EN');
INSERT INTO t_lecture (name)
VALUES ('Praktikum');

-- Feature Timetable
-- Timetable version

DROP TABLE IF EXISTS t_timetable_version;
CREATE SEQUENCE t_timetable_version_seq;

CREATE TABLE t_timetable_version
(
    id            BIGINT DEFAULT t_timetable_version_seq.nextval PRIMARY KEY,
    semester_year VARCHAR(200) NOT NULL,
    version       VARCHAR(100) NOT NULL,
    comment       TEXT
);

INSERT INTO t_timetable_version (semester_year, version, comment)
VALUES ('WS 2021/2022', '0.0.1', 'Über H2 generiert');

-- Timeslot

DROP TABLE IF EXISTS t_timeslot;
CREATE SEQUENCE t_timeslot_seq;

CREATE TABLE t_timeslot
(
    id         BIGINT                DEFAULT t_timeslot_seq.nextval PRIMARY KEY,
    start      TIME         NOT NULL,
    end        TIME         NOT NULL,
    weekday_nr INT          NOT NULL,
    type       VARCHAR(100) NOT NULL DEFAULT 'GENERATED'
);

INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('08:00:00', '09:30:00', 1);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('09:45:00', '11:15:00', 1);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('11:30:00', '13:00:00', 1);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('14:00:00', '15:30:00', 1);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('15:45:00', '17:15:00', 1);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('17:30:00', '19:00:00', 1);

INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('08:00:00', '09:30:00', 2);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('09:45:00', '11:15:00', 2);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('11:30:00', '13:00:00', 2);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('14:00:00', '15:30:00', 2);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('15:45:00', '17:15:00', 2);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('17:30:00', '19:00:00', 2);

INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('08:00:00', '09:30:00', 3);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('09:45:00', '11:15:00', 3);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('11:30:00', '13:00:00', 3);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('14:00:00', '15:30:00', 3);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('15:45:00', '17:15:00', 3);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('17:30:00', '19:00:00', 3);

INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('08:00:00', '09:30:00', 4);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('09:45:00', '11:15:00', 4);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('11:30:00', '13:00:00', 4);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('14:00:00', '15:30:00', 4);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('15:45:00', '17:15:00', 4);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('17:30:00', '19:00:00', 4);

INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('08:00:00', '09:30:00', 5);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('09:45:00', '11:15:00', 5);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('11:30:00', '13:00:00', 5);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('14:00:00', '15:30:00', 5);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('15:45:00', '17:15:00', 5);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('17:30:00', '19:00:00', 5);

INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('08:00:00', '09:30:00', 6);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('09:45:00', '11:15:00', 6);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('11:30:00', '13:00:00', 6);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('14:00:00', '15:30:00', 6);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('15:45:00', '17:15:00', 6);
INSERT INTO t_timeslot (start, end, weekday_nr)
VALUES ('17:30:00', '19:00:00', 6);

-- Timetable

DROP TABLE IF EXISTS t_timetable;
CREATE SEQUENCE t_timetable_seq;

CREATE TABLE t_timetable
(
    id               BIGINT       DEFAULT t_timetable_seq.nextval PRIMARY KEY,
    fk_room          BIGINT NOT NULL,
    fk_lecture       BIGINT NOT NULL,
    fk_lecturer_user BIGINT NOT NULL,
    fk_timeslot      BIGINT NOT NULL,
    fk_version       BIGINT NOT NULL,
    type             VARCHAR(200) DEFAULT 'LECTURE',
    note             TEXT,
    FOREIGN KEY (fk_lecturer_user) REFERENCES t_user (id),
    FOREIGN KEY (fk_lecture) REFERENCES t_lecture (id),
    FOREIGN KEY (fk_room) REFERENCES t_room (id),
    FOREIGN KEY (fk_timeslot) REFERENCES t_timeslot (id),
    FOREIGN KEY (fk_version) REFERENCES t_timetable_version (id),
    CONSTRAINT unique_room_timeslot_version UNIQUE (fk_room, fk_timeslot, fk_version)
);

INSERT INTO t_timetable (fk_room, fk_lecture, fk_lecturer_user, fk_timeslot, fk_version)
VALUES (2, 1, 1, 4, 1);
INSERT INTO t_timetable (fk_room, fk_lecture, fk_lecturer_user, fk_timeslot, fk_version)
VALUES (3, 2, 2, 5, 1);
INSERT INTO t_timetable (fk_room, fk_lecture, fk_lecturer_user, fk_timeslot, fk_version)
VALUES (2, 3, 3, 7, 1);
INSERT INTO t_timetable (fk_room, fk_lecture, fk_lecturer_user, fk_timeslot, fk_version)
VALUES (2, 3, 3, 8, 1);
INSERT INTO t_timetable (fk_room, fk_lecture, fk_lecturer_user, fk_timeslot, fk_version)
VALUES (4, 4, 4, 9, 1);
INSERT INTO t_timetable (fk_room, fk_lecture, fk_lecturer_user, fk_timeslot, fk_version)
VALUES (2, 1, 1, 10, 1);
INSERT INTO t_timetable (fk_room, fk_lecture, fk_lecturer_user, fk_timeslot, fk_version)
VALUES (5, 5, 5, 13, 1);
INSERT INTO t_timetable (fk_room, fk_lecture, fk_lecturer_user, fk_timeslot, fk_version)
VALUES (2, 6, 6, 14, 1);
INSERT INTO t_timetable (fk_room, fk_lecture, fk_lecturer_user, fk_timeslot, fk_version)
VALUES (6, 6, 6, 19, 1);
INSERT INTO t_timetable (fk_room, fk_lecture, fk_lecturer_user, fk_timeslot, fk_version)
VALUES (5, 5, 5, 20, 1);

DROP TABLE IF EXISTS t_semester_lecture_map;

CREATE SEQUENCE t_semester_lecture_map_seq;

CREATE TABLE t_semester_lecture_map
(
    id          BIGINT DEFAULT t_semester_lecture_map_seq.nextval PRIMARY KEY,
    fk_semester BIGINT NOT NULL,
    fk_lecture  BIGINT NOT NULL,
    FOREIGN KEY (fk_semester) REFERENCES t_semester (id),
    FOREIGN KEY (fk_lecture) REFERENCES t_lecture (id)
);

INSERT INTO t_semester_lecture_map (fk_semester, fk_lecture)
VALUES (1, 1);
INSERT INTO t_semester_lecture_map (fk_semester, fk_lecture)
VALUES (1, 2);
INSERT INTO t_semester_lecture_map (fk_semester, fk_lecture)
VALUES (1, 3);
INSERT INTO t_semester_lecture_map (fk_semester, fk_lecture)
VALUES (1, 4);
INSERT INTO t_semester_lecture_map (fk_semester, fk_lecture)
VALUES (1, 5);
INSERT INTO t_semester_lecture_map (fk_semester, fk_lecture)
VALUES (1, 6);
INSERT INTO t_semester_lecture_map (fk_semester, fk_lecture)
VALUES (3, 1);
INSERT INTO t_semester_lecture_map (fk_semester, fk_lecture)
VALUES (3, 2);
INSERT INTO t_semester_lecture_map (fk_semester, fk_lecture)
VALUES (3, 3);
INSERT INTO t_semester_lecture_map (fk_semester, fk_lecture)
VALUES (3, 4);
INSERT INTO t_semester_lecture_map (fk_semester, fk_lecture)
VALUES (3, 5);
INSERT INTO t_semester_lecture_map (fk_semester, fk_lecture)
VALUES (3, 6);