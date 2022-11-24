package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreDao {

    Genre getGenreByID(int genreID);

    List<Genre> getAllGenres();
}
