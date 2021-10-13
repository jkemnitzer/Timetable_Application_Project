DROP TABLE IF EXISTS rooms;

CREATE TABLE rooms
(
    id       int primary key,
    number   varchar(200),
    building varchar(200)
);

INSERT INTO rooms (id, number, building) VALUES (1, 'B109', 'B');
INSERT INTO rooms (id, number, building) VALUES (2, 'G028/29', 'G');