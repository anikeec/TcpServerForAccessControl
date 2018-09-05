use accesscontroldb;

INSERT INTO user(user_id, first_name, second_name, phone_number, email) VALUES(1, "Peter", "Petrov", "0501234567", "petrov@gmail.com");

INSERT INTO card(card_id, card_number, user_id) VALUES(1, "11111111", 1);

INSERT INTO device(device_id, device_number, last_packet_id) VALUES(1, 15, 1);

INSERT INTO event_type VALUES(1,"ACCESS_ALLOW");
INSERT INTO event_type VALUES(2,"ACCESS_DENIED");
INSERT INTO event_type VALUES(3,"ENTER_QUERY");
INSERT INTO event_type VALUES(4,"EXIT_QUERY");

INSERT INTO rule_type VALUES(1, "ENTER");

INSERT INTO rule(card_id, device_id, event_id, rule_type_id, date_begin, date_end) VALUES(1, 1, 3, 1, null, null);