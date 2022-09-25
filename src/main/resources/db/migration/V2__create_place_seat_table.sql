CREATE TABLE t_place_seat (
    id              BIGINT      NOT NULL,
    place_id        BIGINT      NOT NULL,
    sector          VARCHAR(20) NOT NULL,
    row             BIGINT      NOT NULL,
    number_in_row   BIGINT      NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (place_id) REFERENCES t_place (id)
);

CREATE SEQUENCE sq_place_seat
    START WITH 5000
    INCREMENT BY 1
    OWNED BY t_place_seat.id;

