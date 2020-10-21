DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE global_seq_meals RESTART WITH 1;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals(date_time, description, calories, user_id)
VALUES ('2020-10-19 07:00:00.000000', 'frühstück', 1000, 100000),
       ('2020-10-19 12:00:00.000000', 'mittag', 1500, 100000),
       ('2020-10-19 18:00:00.000000', 'abendessen', 500, 100000),
       ('2020-10-20 07:15:00.000000', 'frühstückNachsteTag', 1111, 100000),
       ('2020-10-19 19:00:00.000000', 'abendessen2', 510, 100001);