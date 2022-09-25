CREATE TABLE t_place (
    id      BIGINT        NOT NULL,
    name    VARCHAR(200)  NOT NULL,
    city    VARCHAR(200)  NOT NULL,

    PRIMARY KEY (id)
);

CREATE SEQUENCE sq_place
    START WITH 5000
    INCREMENT BY 1
    OWNED BY t_place.id;
