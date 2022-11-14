create table IF NOT EXISTS FRIENDS
(
    USER_ID   INTEGER not null,
    FRIEND_ID INTEGER not null,
    constraint ACCEPTED_FRIENDS_PK
        primary key (USER_ID, FRIEND_ID)
);

create table IF NOT EXISTS FRIENDSHIP_REQUESTS
(
    RECIPIENT_ID INTEGER not null,
    SENDER_ID    INTEGER not null,
    constraint "Accepted_Friends_pk"
        primary key (RECIPIENT_ID, SENDER_ID)
);

create table IF NOT EXISTS GENRES
(
    GENRE_ID   INTEGER auto_increment,
    GENRE_NAME CHARACTER VARYING,
    constraint "GENRES_pk"
        primary key (GENRE_ID)
);

create table IF NOT EXISTS GENRES_OF_FILMS
(
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint "Genres_Of_Films_pk"
        primary key (FILM_ID),
    constraint "GENRES_OF_FILMS_GENRES_null_fk"
        foreign key (GENRE_ID) references GENRES
);

create table IF NOT EXISTS MPA_RATING
(
    MPA_RATING_ID INTEGER auto_increment,
    RATING_NAME   CHARACTER VARYING,
    constraint "MPA_Rating_pk"
        primary key (MPA_RATING_ID)
);

create table IF NOT EXISTS FILMS
(
    FILM_NAME     CHARACTER VARYING not null,
    RELEASE_DATE  DATE,
    FILM_ID       INTEGER auto_increment,
    DURATION      INTEGER,
    DESCRIPTION   CHARACTER VARYING,
    MPA_RATING_ID INTEGER,
    constraint PRIMARY_KEY
        primary key (FILM_ID),
    constraint "FILM_MPA_Rating_null_fk"
        foreign key (MPA_RATING_ID) references MPA_RATING
);

create table IF NOT EXISTS USERS
(
    USER_ID   INTEGER auto_increment,
    EMAIL     CHARACTER VARYING not null,
    LOGIN     CHARACTER VARYING not null,
    USER_NAME CHARACTER VARYING,
    BIRTHDAY  DATE,
    constraint "Users_pk"
        primary key (USER_ID)
);

create table IF NOT EXISTS USERS_LIKED_FILM
(
    USER_ID INTEGER not null,
    FILM_ID INTEGER not null,
    constraint USERS_LIKED_FILM_PK
        primary key (USER_ID, FILM_ID)
);

