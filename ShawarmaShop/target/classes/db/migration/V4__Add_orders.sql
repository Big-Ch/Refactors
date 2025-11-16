INSERT INTO orders (id, address, city, date, email, first_name, last_name, phone_number, total_price, user_id)
VALUES (1, 'Lenina avenue 51', 'Barnaul', '2024-03-02', 'test123@test.com', 'Ahmet', 'Ahmetov', 1234567890, 840, 2);
INSERT INTO orders (id, address, city, date, email, first_name, last_name, phone_number, total_price, user_id)
VALUES (2, 'Lenina avenue 51', 'Barnaul', '2024-03-02', 'test123@test.com', 'Ahmet', 'Ahmetov', 1234567890, 240, 2);
INSERT INTO orders (id, address, city, date, email, first_name, last_name, phone_number, total_price, user_id)
VALUES (3, 'Zavodskaya street 4', 'Novoaltaisk', '2024-03-02', 'ivan123@test.com', 'Ivan', 'Ivanov', 1234567890, 163, 3);
INSERT INTO orders (id, address, city, date, email, first_name, last_name, phone_number, total_price, user_id)
VALUES (4, 'Zavodskaya street 4', 'Novoaltaisk', '2024-03-02', 'ivan123@test.com', 'Ivan', 'Ivanov', 1234567890, 780, 3);
INSERT INTO orders (id, address, city, date, email, first_name, last_name, phone_number, total_price, user_id)
VALUES (5, 'Zavodskaya street 4', 'Novoaltaisk', '2024-03-02', 'ivan123@test.com', 'Ivan', 'Ivanov', 1234567890, 196, 3);

INSERT INTO orders_shawarmas (order_id, shawarmas_id) VALUES (1, 2);
INSERT INTO orders_shawarmas (order_id, shawarmas_id) VALUES (1, 16);
INSERT INTO orders_shawarmas (order_id, shawarmas_id) VALUES (2, 24);
INSERT INTO orders_shawarmas (order_id, shawarmas_id) VALUES (2, 21);
INSERT INTO orders_shawarmas (order_id, shawarmas_id) VALUES (3, 5);
INSERT INTO orders_shawarmas (order_id, shawarmas_id) VALUES (3, 12);
INSERT INTO orders_shawarmas (order_id, shawarmas_id) VALUES (3, 8);
INSERT INTO orders_shawarmas (order_id, shawarmas_id) VALUES (4, 19);
INSERT INTO orders_shawarmas (order_id, shawarmas_id) VALUES (4, 25);
INSERT INTO orders_shawarmas (order_id, shawarmas_id) VALUES (5, 4);
INSERT INTO orders_shawarmas (order_id, shawarmas_id) VALUES (5, 6);