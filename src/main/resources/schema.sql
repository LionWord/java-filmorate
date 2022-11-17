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
    constraint "USER_INFO_pk"
        primary key (EMAIL)
);

create table IF NOT EXISTS FRIENDS
(
    USER_EMAIL   CHARACTER VARYING(256) not null,
    FRIEND_EMAIL CHARACTER VARYING(256) not null,
    constraint "FRIENDS_pk"
        primary key (USER_EMAIL, FRIEND_EMAIL),
    constraint "FRIEND_EMAIL FK"
        foreign key (FRIEND_EMAIL) references USER_INFO,
    constraint "USER_EMAIL FK"
        foreign key (USER_EMAIL) references USER_INFO
);

create table IF NOT EXISTS FRIENDSHIP_REQUESTS
(
    RECIPIENT_EMAIL CHARACTER VARYING(256) not null,
    SENDER_EMAIL    CHARACTER VARYING(256) not null,
    constraint "FRIENDSHIP_REQUESTS_pk"
        primary key (RECIPIENT_EMAIL, SENDER_EMAIL),
    constraint "RECIPIENT FK"
        foreign key (RECIPIENT_EMAIL) references USER_INFO,
    constraint "SENDER FK"
        foreign key (SENDER_EMAIL) references USER_INFO
);

create table IF NOT EXISTS USERS_LIKED_FILM
(
    USER_EMAIL CHARACTER VARYING(256) not null,
    FILM_ID    INTEGER                not null,
    constraint "USERS_LIKED_FILM_pk"
        primary key (USER_EMAIL, FILM_ID),
    constraint "USERS_LIKED_FILM_FILMS_null_fk"
        foreign key (FILM_ID) references FILMS,
    constraint "USERS_LIKED_FILM_USER_INFO_null_fk"
        foreign key (USER_EMAIL) references USER_INFO
);

