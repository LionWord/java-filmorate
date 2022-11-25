package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    void addFilm(Film film);

    void deleteFilm(int filmID);

    void modifyFilm(Film film);

    List<Film> getAllFilms();

    boolean filmIsPresent(int filmID);

    Film getFilm(int filmID);
}
