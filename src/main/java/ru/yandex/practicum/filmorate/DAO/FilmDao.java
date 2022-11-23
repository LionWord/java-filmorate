package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FilmDao {

    Film addFilm(Film film);

    Film updateFilm(Film film);

    boolean removeFilmByID(int filmID);

    Optional<Film> getFilmByID(int filmID);

    Film makeFilm(ResultSet rs) throws SQLException;

    Optional<List<Film>> getAllFilms();

}
