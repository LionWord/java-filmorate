package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    void addFilm(Film film);

    void deleteFilm(int filmID);

    void modifyFilm(Film film);

    Optional<List<Film>> getAllFilms();

    boolean filmIsPresent(int filmID);

    Optional<Film> getFilm(int filmID);
}
