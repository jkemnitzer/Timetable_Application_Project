DROP TABLE IF EXISTS t_token;

CREATE SEQUENCE t_token_seq;

CREATE TABLE t_token
(
    id       BIGINT                       DEFAULT t_token_seq.nextval PRIMARY KEY,
    token    VARCHAR(200) UNIQUE NOT NULL,
    valid_to DATETIME            NOT NULL DEFAULT DATEADD(minute, 5, NOW()),
    fk_user  BIGINT              NOT NULL,
    FOREIGN KEY (fk_user) REFERENCES t_user (id)
)