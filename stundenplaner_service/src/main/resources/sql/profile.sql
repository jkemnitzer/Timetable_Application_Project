CREATE SEQUENCE t_profile_seq;
CREATE TABLE t_profile
(
    id            bigint DEFAULT t_profile_seq.nextval PRIMARY KEY NOT NULL,
    sws           INTEGER,
    fk_user_id    bigint                                           NOT NULL,
    fk_faculty_id bigint                                           NOT NULL,
    FOREIGN KEY (fk_user_id) REFERENCES t_user (id),
    FOREIGN KEY (fk_faculty_id) REFERENCES t_faculty (id)
);

CREATE SEQUENCE t_delegated_lecturer_seq;
CREATE TABLE t_delegated_lecturer
(
    id BIGINT DEFAULT t_delegated_lecturer_seq.nextval PRIMARY KEY NOT NULL,
    sws INTEGER,
    fk_user_id BIGINT NOT NULL,
    FOREIGN KEY (fk_user_id) REFERENCES t_user (id)
);

CREATE SEQUENCE t_module_preference_seq;
CREATE TABLE t_module_preference
(
    id BIGINT DEFAULT t_module_preference_seq.nextval PRIMARY KEY NOT NULL,
    priority INTEGER,
    fk_profile_id BIGINT NOT NULL,
    fk_lecture_id BIGINT NOT NULL,
    FOREIGN KEY (fk_profile_id) REFERENCES t_profile (id),
    FOREIGN KEY (fk_lecture_id) REFERENCES t_lecture (id)
);

CREATE SEQUENCE t_profile_delegated_lecturer_map_seq;
CREATE TABLE t_profile_delegated_lecturer_map
(
    id BIGINT DEFAULT t_profile_delegated_lecturer_map_seq.nextval PRIMARY KEY NOT NULL,
    fk_profile_id BIGINT NOT NULL,
    fk_delegated_lecturer_id BIGINT NOT NULL,
    FOREIGN KEY (fk_profile_id) REFERENCES t_profile (id),
    FOREIGN KEY (fk_delegated_lecturer_id) REFERENCES t_profile (id)
);

CREATE SEQUENCE t_timeslot_preference_seq;
CREATE TABLE t_timeslot_preference
(
    id             BIGINT DEFAULT t_timeslot_preference_seq.nextval PRIMARY KEY NOT NULL,
    priority       INTEGER,
    forced         BOOL,
    fk_timeslot_id BIGINT                                                       NOT NULL,
    fk_profile_id  bigint                                                       NOT NULL,
    FOREIGN KEY (fk_timeslot_id) REFERENCES t_timeslot (id),
    FOREIGN KEY (fk_profile_id) REFERENCES t_profile (id)
);

INSERT INTO t_delegated_lecturer (id, sws, fk_user_id)
VALUES (1, 2, 7);
INSERT INTO t_delegated_lecturer (id, sws, fk_user_id)
VALUES (2, 4, 7);
INSERT INTO t_delegated_lecturer (id, sws, fk_user_id)
VALUES (3, 2, 7);
INSERT INTO t_delegated_lecturer (id, sws, fk_user_id)
VALUES (4, 6, 7);

INSERT INTO t_profile(id, sws, fk_user_id, fk_faculty_id)
VALUES (1, 18, 1, 1);
INSERT INTO t_profile(id, sws, fk_user_id, fk_faculty_id)
VALUES (2, 18, 2, 2);
INSERT INTO t_profile(id, sws, fk_user_id, fk_faculty_id)
VALUES (3, 18, 3, 2);
INSERT INTO t_profile(id, sws, fk_user_id, fk_faculty_id)
VALUES (4, 18, 4, 1);
INSERT INTO t_profile(id, sws, fk_user_id, fk_faculty_id)
VALUES (5, 18, 5, 3);
INSERT INTO t_profile(id, sws, fk_user_id, fk_faculty_id)
VALUES (6, 18, 6, 2);
INSERT INTO t_profile(id, sws, fk_user_id, fk_faculty_id)
VALUES (7, 18, 7, 3);

INSERT INTO t_module_preference (id, priority, fk_profile_id, fk_lecture_id)
VALUES (1, 100, 1, 1);
INSERT INTO t_module_preference (id, priority, fk_profile_id, fk_lecture_id)
VALUES (2, 80, 2, 2);
INSERT INTO t_module_preference (id, priority, fk_profile_id, fk_lecture_id)
VALUES (3, 95, 3, 3);
INSERT INTO t_module_preference (id, priority, fk_profile_id, fk_lecture_id)
VALUES (4, 42, 4, 4);
INSERT INTO t_module_preference (id, priority, fk_profile_id, fk_lecture_id)
VALUES (5, 50, 5, 5);
INSERT INTO t_module_preference (id, priority, fk_profile_id, fk_lecture_id)
VALUES (6, 60, 6, 6);

INSERT INTO t_profile_delegated_lecturer_map (id, fk_profile_id, fk_delegated_lecturer_id)
VALUES (1, 1, 4);
INSERT INTO t_profile_delegated_lecturer_map (id, fk_profile_id, fk_delegated_lecturer_id)
VALUES (2, 2, 3);
INSERT INTO t_profile_delegated_lecturer_map (id, fk_profile_id, fk_delegated_lecturer_id) VALUES (3, 5, 2);
INSERT INTO t_profile_delegated_lecturer_map (id, fk_profile_id, fk_delegated_lecturer_id) VALUES (4, 6, 1);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (1, 100, 1, 1, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (2, 100, 2, 1, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (3, 100, 3, 1, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (4, 100, 4, 1, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (5, 100, 5, 1, FALSE);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (6, 100, 1, 2, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (7, 100, 2, 2, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (8, 100, 3, 2, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (9, 100, 4, 2, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (10, 100, 5, 2, FALSE);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (11, 100, 1, 3, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (12, 100, 2, 3, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (13, 100, 3, 3, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (14, 100, 4, 3, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (15, 100, 5, 3, FALSE);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (16, 0, 1, 4, TRUE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (17, 0, 2, 4, TRUE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (18, 100, 3, 4, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (19, 100, 4, 4, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (20, 100, 5, 4, FALSE);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (21, 100, 1, 5, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (22, 100, 2, 5, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (23, 0, 3, 5, TRUE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (24, 100, 4, 5, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (25, 100, 5, 5, FALSE);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (26, 100, 1, 6, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (27, 100, 2, 6, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (28, 100, 3, 6, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (29, 100, 4, 6, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (30, 100, 5, 6, FALSE);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (31, 100, 1, 7, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (32, 100, 2, 7, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (33, 100, 3, 7, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (34, 100, 4, 7, FALSE);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id, forced) VALUES (35, 100, 5, 7, FALSE);