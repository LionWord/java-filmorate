insert into MPA_RATING (MPA_ID, RATING_NAME)
values (1, 'G'),
       (2, 'PG'),
       (3, 'PG-13'),
       (4, 'R'),
       (5, 'NC-17');

insert into GENRES (GENRE_NAME)
values ('Комедия'),
       ('Драма'),
       ('Мультфильм'),
       ('Триллер'),
       ('Документальный'),
       ('Боевик');

insert into USER_INFO (EMAIL, LOGIN, USER_NAME, BIRTHDAY)
values ('mail@yandex.ru', 'dolore', 'est adipisicing', '1976-09-20'),
       ('fail@ya.ru', 'olotor', 'disgustin', '1973-01-01'),
       ('trail@rambler.ru', 'jack', 'olddad', '1951-02-02');

insert into FILMS (FILM_NAME, RELEASE_DATE, DURATION, DESCRIPTION, MPA_RATING_ID)
values ('New film', '1999-04-30', 120, 'New film about friends', 3),
       ('Opop', '1998-04-30', 150, 'On opop', 2),
       ('Coco', '1997-04-30', 110, 'On coco', 1);


