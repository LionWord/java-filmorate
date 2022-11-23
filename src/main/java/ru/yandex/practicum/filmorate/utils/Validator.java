package ru.yandex.practicum.filmorate.utils;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.time.LocalDate;


public class Validator {
    private Validator() {

    }

    public static boolean isValidFilm(Film film) {
        return !film.getName().isEmpty()
                && film.getDescription().length() <= 200
                && film.getDuration() > 0
                && film.getReleaseDate().after(Date.valueOf(LocalDate.of(1895, 12, 28)));
    }

    public static boolean isValidUser(User user) {
        return !user.getEmail().isEmpty()
                && user.getEmail().contains("@")
                && !user.getBirthday().isAfter(LocalDate.now())
                && !user.getLogin().contains(" ")
                && !user.getLogin().isEmpty()
                && user.getLogin() != null;
    }

}
