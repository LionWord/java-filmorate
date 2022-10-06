package ru.yandex.practicum.filmorate.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class IdAssigner {
    private static int filmID = 0;
    private static int userID = 0;

    private IdAssigner() {}

    public static void registerNewFilm() {
        filmID++;
    }

    public static void registerNewUser() {
        userID++;
    }

    public static void removeFilm() {
        if (filmID > 0) {
            filmID--;
        } else {
            log.debug("Trying to set filmID value below zero. Value won't drop below zero.");
        }
    }

    public static void removeUser() {
        if (userID > 0) {
            userID--;
        } else {
            log.debug("Trying to set userID value below zero. Value won't drop below zero.");
        }
    }

    public static void resetFilmID() {
        filmID = 0;
    }

    public static void resetUserID() {
        userID = 0;
    }

    public static void resetAll() {
        filmID = 0;
        userID = 0;
    }

    public static void customiseFilmIDValue(int filmID) {
        if (filmID < 0) {
            log.debug("Trying to set filmID value below zero. Value won't drop below zero.");
        } else {
            IdAssigner.filmID = filmID;
        }

    }

    public static void customiseUserIDValue(int userID) {
        if (userID < 0) {
            log.debug("Trying to set userID value below zero. Value won't drop below zero.");
        } else {
            IdAssigner.userID = userID;
        }
    }
}
