create table t_event (
    id                BIGINT         NOT NULL,
    name              VARCHAR(50)    NOT NULL,
    description       VARCHAR(250)   NOT NULL,
    start_date_time   TIMESTAMP      NOT NULL,

    PRIMARY KEY (id)
);

CREATE SEQUENCE sq_event
    START WITH 5000
    INCREMENT BY 1
    OWNED BY t_event.id;

