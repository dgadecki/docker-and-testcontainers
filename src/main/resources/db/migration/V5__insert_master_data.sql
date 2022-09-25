-- Events
INSERT INTO t_event(id, name, description, start_date_time)
VALUES (1, 'Polska - Argentyna', 'Mecz fazy grupowej - grupa C', '2022-11-26 14:00:00'),
       (2, 'Polska - Meksyk', 'Mecz fazy grupowej - grupa C', '2022-11-22 17:00:00'),
       (3, 'Polska - Arabia Saudyjska', 'Mecz fazy grupowej - grupa C', '2022-11-30 20:00:00');

-- Places
INSERT INTO t_place(id, name, city)
VALUES (1, 'Al Bayt Stadium', 'Al Khor'),
       (2, 'Lusail Stadium', 'Lusail'),
       (3, 'Ahmad Bin Ali Stadium', 'Umm Al Afaei'),
       (4, 'Al Janoub Stadium', 'Al Wakrah'),
       (5, 'Al Thumama Stadium', 'Al Thumama'),
       (6, 'Education City Stadium', 'Al Rayyan'),
       (7, 'Khalifa International Stadium', 'Aspire'),
       (8, 'Stadium 974', 'Ras Bu Abboud');

-- Seats
INSERT INTO t_place_seat(id, place_id, sector, row, number_in_row)
VALUES (1, 1, 'A', 10, 1),
       (2, 1, 'A', 10, 2),
       (3, 1, 'A', 10, 3),
       (4, 1, 'A', 10, 4),
       (5, 1, 'A', 10, 5),
       (6, 1, 'A', 10, 5),
       (7, 1, 'A', 10, 5),
       (8, 1, 'A', 10, 5),
       (9, 1, 'A', 10, 5);

-- Tickets
INSERT INTO t_event_ticket(id, event_id, place_seat_id, user_id, booking_date_time, sale_date_time)
VALUES (1, 1, 1, null, null, null),
       (2, 1, 2, null, null, null),
       (3, 1, 3, null, null, null),
       (4, 1, 4, null, null, null),
       (5, 1, 5, null, null, null),
       (6, 1, 6, null, null, null),
       (7, 1, 7, null, null, null),
       (8, 1, 8, null, null, null),
       (9, 1, 9, null, null, null);
