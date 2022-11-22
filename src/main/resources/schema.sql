-- we don't know how to generate root <with-no-name> (class Root) :(
create table IF NOT EXISTS GENRES
(
    GENRE_ID   INTEGER auto_increment,
    GENRE_NAME CHARACTER VARYING,
    constraint "GENRES_pk"
        primary key (GENRE_ID)
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
    FILM_NAME     CHARACTER VARYING(176) not null,
    RELEASE_DATE  DATE,
    FILM_ID       INTEGER auto_increment,
    DURATION      INTEGER,
    DESCRIPTION   CHARACTER VARYING(200),
    MPA_RATING_ID INTEGER,
    RATE          INTEGER,
    constraint PRIMARY_KEY
        primary key (FILM_ID),
    constraint "FILM_MPA_Rating_null_fk"
        foreign key (MPA_RATING_ID) references MPA_RATING
);

create table IF NOT EXISTS GENRES_OF_FILMS
(
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint "GENRES_OF_FILMS_pk"
        primary key (FILM_ID, GENRE_ID),
    constraint "FILM_ID FK"
        foreign key (FILM_ID) references FILMS,
    constraint "GENRE_ID FK"
        foreign key (GENRE_ID) references GENRES
);

create table IF NOT EXISTS USER_INFO
(
    EMAIL     CHARACTER VARYING(256) not null,
    LOGIN     CHARACTER VARYING(256) not null,
    USER_NAME CHARACTER VARYING(256),
    BIRTHDAY  DATE,
    ID        INTEGER auto_increment
        unique,
    constraint "USER_INFO_pk"
        primary key (EMAIL)
);

create table IF NOT EXISTS FRIENDS
(
    USER_ID   INTEGER not null,
    FRIEND_ID INTEGER not null,
    constraint "FRIENDS_pk"
        primary key (USER_ID, FRIEND_ID),
    constraint "FRIEND_EMAIL FK"
        foreign key (FRIEND_ID) references USER_INFO (ID),
    constraint "USER_EMAIL FK"
        foreign key (USER_ID) references USER_INFO (ID)
);

create table IF NOT EXISTS FRIENDSHIP_REQUESTS
(
    RECIPIENT_ID INTEGER not null,
    SENDER_ID    INTEGER not null,
    constraint "FRIENDSHIP_REQUESTS_pk"
        primary key (RECIPIENT_ID, SENDER_ID),
    constraint "RECIPIENT FK"
        foreign key (RECIPIENT_ID) references USER_INFO (ID),
    constraint "SENDER FK"
        foreign key (SENDER_ID) references USER_INFO (ID)
);

create table IF NOT EXISTS USERS_LIKED_FILM
(
    USER_ID INTEGER not null,
    FILM_ID INTEGER not null,
    constraint "USERS_LIKED_FILM_pk"
        primary key (USER_ID, FILM_ID),
    constraint "USERS_LIKED_FILM_FILMS_null_fk"
        foreign key (FILM_ID) references FILMS,
    constraint "USERS_LIKED_FILM_USER_INFO_null_fk"
        foreign key (USER_ID) references USER_INFO (ID)
);