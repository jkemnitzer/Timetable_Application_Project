CREATE SEQUENCE t_timeslot_seq;
CREATE TABLE t_timeslot
(
    id BIGINT DEFAULT t_timeslot_seq.nextval PRIMARY KEY NOT NULL,
    weekday INTEGER,
    start TIME,
    end TIME,
    is_custom_timeslot BOOLEAN
);

CREATE SEQUENCE t_profile_seq;
CREATE TABLE t_profile
(
    id BIGINT DEFAULT t_profile_seq.nextval PRIMARY KEY NOT NULL,
    sws INTEGER,
    fk_user_id BIGINT NOT NULL,
    FOREIGN KEY (fk_user_id) REFERENCES t_user (id)
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
    FOREIGN KEY (fk_profile_id) REFERENCES t_profile (id)
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
    id BIGINT DEFAULT t_timeslot_preference_seq.nextval PRIMARY KEY NOT NULL,
    priority INTEGER,
    fk_timeslot_id BIGINT NOT NULL,
    fk_profile_id BIGINT NOT NULL,
    FOREIGN KEY (fk_timeslot_id) REFERENCES t_timeslot (id),
    FOREIGN KEY (fk_profile_id) REFERENCES t_profile (id)
);


--INSERT INTO t_user (id, name) VALUES (1, 'Prof. Dr. Günther Köhler');
--INSERT INTO t_user (id, name) VALUES (2, 'Prof. Dr. Jörg Scheidt');
--INSERT INTO t_user (id, name) VALUES (3, 'Prof. Dr. Michael Seidel');
--INSERT INTO t_user (id, name) VALUES (4, 'Pfarrer Wolfram Lehmann');
--INSERT INTO t_user (id, name) VALUES (5, 'Prof. Dr. Beatrix Weber');
--INSERT INTO t_user (id, name) VALUES (6, 'Prof. Dr. Christian Groth');
--INSERT INTO t_user (id, name) VALUES (7, 'Steffen Unger');

INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (1, 1, '08:00', '09:30', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (2, 1, '09:45', '11:15', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (3, 1, '11:30', '13:00', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (4, 1, '14:00', '15:30', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (5, 1, '15:45', '17:15', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (6, 1, '17:30', '19:00', false);

INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (7, 2, '08:00', '09:30', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (8, 2, '09:45', '11:15', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (9, 2, '11:30', '13:00', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (10, 2, '14:00', '15:30', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (11, 2, '15:45', '17:15', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (12, 2, '17:30', '19:00', false);

INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (13, 3, '08:00', '09:30', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (14, 3, '09:45', '11:15', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (15, 3, '11:30', '13:00', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (16, 3, '14:00', '15:30', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (17, 3, '15:45', '17:15', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (18, 3, '17:30', '19:00', false);

INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (19, 4, '08:00', '09:30', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (20, 4, '09:45', '11:15', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (21, 4, '11:30', '13:00', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (22, 4, '14:00', '15:30', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (23, 4, '15:45', '17:15', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (24, 4, '17:30', '19:00', false);

INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (25, 5, '08:00', '09:30', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (26, 5, '09:45', '11:15', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (27, 5, '11:30', '13:00', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (28, 5, '14:00', '15:30', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (29, 5, '15:45', '17:15', false);
INSERT INTO t_timeslot (id, weekday, start, end, is_custom_timeslot) VALUES (30, 5, '17:30', '19:00', false);

INSERT INTO t_delegated_lecturer (id, sws, fk_user_id) VALUES (1, 2, 7);
INSERT INTO t_delegated_lecturer (id, sws, fk_user_id) VALUES (2, 4, 7);
INSERT INTO t_delegated_lecturer (id, sws, fk_user_id) VALUES (3, 2, 7);
INSERT INTO t_delegated_lecturer (id, sws, fk_user_id) VALUES (4, 6, 7);

INSERT INTO t_profile(id, sws, fk_user_id) VALUES (1, 18, 1);
INSERT INTO t_profile(id, sws, fk_user_id) VALUES (2, 18, 2);
INSERT INTO t_profile(id, sws, fk_user_id) VALUES (3, 18, 3);
INSERT INTO t_profile(id, sws, fk_user_id) VALUES (4, 18, 4);
INSERT INTO t_profile(id, sws, fk_user_id) VALUES (5, 18, 5);
INSERT INTO t_profile(id, sws, fk_user_id) VALUES (6, 18, 6);
INSERT INTO t_profile(id, sws, fk_user_id) VALUES (7, 18, 7);

INSERT INTO t_module_preference (id, priority, fk_profile_id) VALUES (1, 100, 1);
INSERT INTO t_module_preference (id, priority, fk_profile_id) VALUES (2, 80, 2);
INSERT INTO t_module_preference (id, priority, fk_profile_id) VALUES (3, 95, 3);
INSERT INTO t_module_preference (id, priority, fk_profile_id) VALUES (4, 42, 4);
INSERT INTO t_module_preference (id, priority, fk_profile_id) VALUES (5, 50, 5);
INSERT INTO t_module_preference (id, priority, fk_profile_id) VALUES (6, 60, 6);

INSERT INTO t_profile_delegated_lecturer_map (id, fk_profile_id, fk_delegated_lecturer_id) VALUES (1, 1, 4);
INSERT INTO t_profile_delegated_lecturer_map (id, fk_profile_id, fk_delegated_lecturer_id) VALUES (2, 2, 3);
INSERT INTO t_profile_delegated_lecturer_map (id, fk_profile_id, fk_delegated_lecturer_id) VALUES (3, 5, 2);
INSERT INTO t_profile_delegated_lecturer_map (id, fk_profile_id, fk_delegated_lecturer_id) VALUES (4, 6, 1);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (1, 100, 1, 1);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (2, 100, 2, 1);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (3, 100, 3, 1);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (4, 100, 4, 1);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (5, 100, 5, 1);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (6, 100, 1, 2);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (7, 100, 2, 2);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (8, 100, 3, 2);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (9, 100, 4, 2);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (10, 100, 5, 2);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (11, 100, 1, 3);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (12, 100, 2, 3);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (13, 100, 3, 3);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (14, 100, 4, 3);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (15, 100, 5, 3);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (16, 100, 1, 4);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (17, 100, 2, 4);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (18, 100, 3, 4);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (19, 100, 4, 4);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (20, 100, 5, 4);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (21, 100, 1, 5);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (22, 100, 2, 5);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (23, 100, 3, 5);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (24, 100, 4, 5);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (25, 100, 5, 5);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (26, 100, 1, 6);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (27, 100, 2, 6);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (28, 100, 3, 6);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (29, 100, 4, 6);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (30, 100, 5, 6);

INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (31, 100, 1, 7);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (32, 100, 2, 7);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (33, 100, 3, 7);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (34, 100, 4, 7);
INSERT INTO t_timeslot_preference (id, priority, fk_timeslot_id, fk_profile_id) VALUES (35, 100, 5, 7);