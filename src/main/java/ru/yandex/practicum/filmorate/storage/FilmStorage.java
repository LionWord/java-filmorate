package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {

    void addFilm(Film film);

    void deleteFilm(Film film);

    void modifyFilm(Film film);
}
