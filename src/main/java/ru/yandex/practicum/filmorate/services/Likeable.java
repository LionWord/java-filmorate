package ru.yandex.practicum.filmorate.services;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface Likeable {
    void addLike(int filmID, String userEmail);
    void removeLike(int filmID, String userEmail);
    List<Film> getMostPopularFilms(int topFilmsAmount);
    void checkFilmAndUserPresence(int filmID, String userEmail);
}
