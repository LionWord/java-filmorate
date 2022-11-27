delete
from FRIENDS;

delete
from FRIENDSHIP_REQUESTS;

delete
from GENRES_OF_FILMS;

delete
from GENRES;

delete
from USERS_LIKED_FILM;

delete
from FILMS;

delete
from MPA_RATING;

delete
from USER_INFO;

alter table USER_INFO
    alter column ID
        restart with 1;

alter table FILMS
    alter column FILM_ID
        restart with 1;

alter table GENRES
    alter column GENRE_ID
        restart with 1;

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




