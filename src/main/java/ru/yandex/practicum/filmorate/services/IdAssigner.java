package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdAssigner {
    private static int userID = 1;
    private static int filmID = 1;

    public static void increaseUserID() {
        userID += 1;
        log.info("Current userID value = " + userID);
    }

    public static int getUserID() {
        return userID;
    }

    public static void increaseFilmID() {
        filmID += 1;
        log.info("Current filmID value = " + filmID);
    }

    public static int getFilmID() {
        return filmID;
    }

}
