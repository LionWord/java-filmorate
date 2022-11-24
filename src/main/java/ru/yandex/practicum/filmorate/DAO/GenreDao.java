package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> getGenresByID(Integer ... genresID);
    Genre getGenre(int genreID);
    List<Genre> getAllGenres();
    List<Integer> getGenresOfFilm(int filmID);
    void connectGenreAndFilm(Film film);
    void disconnectGenreAndFilm(Film film);
}
