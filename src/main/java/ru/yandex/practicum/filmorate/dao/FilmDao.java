package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface FilmDao {

    Film addFilm(Film film);

    Film updateFilm(Film film);

    boolean removeFilmByID(int filmID);

    Film getFilmByID(int filmID);
    Film setFilmGenres(Film film);
    Film makeFilm(ResultSet rs) throws SQLException;
    List<Film> getAllFilms();

}
