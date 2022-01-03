
DROP TABLE IF EXISTS t_roomfeature;

CREATE SEQUENCE t_roomfeature_seq;
CREATE TABLE t_roomfeature
(
    id       BIGINT     DEFAULT t_roomfeature_seq.nextval PRIMARY KEY NOT NULL,
    feature  VARCHAR(200)
);

INSERT INTO t_roomfeature (feature) VALUES ('Gruppenarbeit');
INSERT INTO t_roomfeature (feature) VALUES ('Labor');
INSERT INTO t_roomfeature (feature) VALUES ('Beamer');
INSERT INTO t_roomfeature (feature) VALUES ('Window PC');
INSERT INTO t_roomfeature (feature) VALUES ('MAC PC');
INSERT INTO t_roomfeature (feature) VALUES ('3D VIDEO');




DROP TABLE IF EXISTS t_roomfeature_map;

CREATE SEQUENCE t_roomfeature_map_seq;
CREATE TABLE t_roomfeature_map
(
    id  BIGINT DEFAULT t_roomfeature_map_seq.nextval PRIMARY KEY,
    fk_room_id BIGINT NOT NULL,
    fk_feature_id BIGINT NOT NULL,
    FOREIGN KEY (fk_room_id) REFERENCES t_room(id),
    FOREIGN KEY (fk_feature_id) REFERENCES t_roomfeature(id)
);

INSERT INTO t_roomfeature_map(fk_room_id, fk_feature_id) VALUES ( 1,1 );
INSERT INTO t_roomfeature_map(fk_room_id, fk_feature_id) VALUES ( 1,2 );
INSERT INTO t_roomfeature_map(fk_room_id, fk_feature_id) VALUES ( 1,3 );