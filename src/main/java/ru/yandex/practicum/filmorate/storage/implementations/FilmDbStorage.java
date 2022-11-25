package ru.yandex.practicum.filmorate.storage.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Repository
public class FilmDbStorage implements FilmStorage {

    private final FilmDao filmDao;

    @Autowired
    public FilmDbStorage(FilmDao filmDao) {
        this.filmDao = filmDao;
    }


    @Override
    public void addFilm(Film film) {
        filmDao.addFilm(film);
    }

    @Override
    public void deleteFilm(int filmID) {
        filmDao.removeFilmByID(filmID);
    }

    @Override
    public void modifyFilm(Film film) {
        filmDao.updateFilm(film);
    }

    @Override
    public List<Film> getAllFilms() {
       return filmDao.getAllFilms();
    }

    @Override
    public boolean filmIsPresent(int filmID) {
        return filmDao.getFilmByID(filmID) != null;
    }

    @Override
    public Film getFilm(int filmID) {
        return filmDao.getFilmByID(filmID);
    }
}
