CREATE TABLE t_event_ticket (
    id                      BIGINT    NOT NULL,
    event_id                BIGINT    NOT NULL,
    place_seat_id           BIGINT    NOT NULL,
    user_id                 UUID,
    booking_date_time       TIMESTAMP,
    sale_date_time          TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (event_id) REFERENCES t_event (id),
    FOREIGN KEY (place_seat_id) REFERENCES t_place_seat(id)
);

CREATE SEQUENCE sq_event_ticket
    START WITH 5000
    INCREMENT BY 1
    OWNED BY t_event_ticket.id;
