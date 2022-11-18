package ru.yandex.practicum.filmorate.services;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface Likeable {
    void addLike(int filmID, int userID);
    void removeLike(int filmID, int userID);
    List<Film> getMostPopularFilms(int topFilmsAmount);
    public void checkFilmAndUserPresence(int filmID, int userID);
}
