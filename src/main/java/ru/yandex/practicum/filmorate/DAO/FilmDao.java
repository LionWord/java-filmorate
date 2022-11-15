package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmDao {

    Film addFilm(Film film);

    boolean removeFilm(Film film);

    Film updateFilm(Film film);

    boolean removeFilmByID(int ID);

    Film updateFilmByID(int ID);


}
