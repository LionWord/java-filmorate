package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.LikesDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.dao.implementations.FilmDaoImpl;
import ru.yandex.practicum.filmorate.dao.implementations.LikesDaoImpl;
import ru.yandex.practicum.filmorate.dao.implementations.UserDaoImpl;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService implements Likeable {

    private final FilmDao filmDao;
    private final UserDao userDao;
    private final LikesDao likesDao;

    @Autowired
    public FilmService(FilmDaoImpl filmDao, UserDaoImpl userDao, LikesDaoImpl likesDao) {
        this.filmDao = filmDao;
        this.userDao = userDao;
        this.likesDao = likesDao;
    }

    public Film addFilm(Film film) {
        return filmDao.addFilm(film);
    }

    public void deleteFilm(int filmID) {
        filmDao.removeFilmByID(filmID);
    }

    public Film modifyFilm(Film film) {
        return filmDao.updateFilm(film);
    }

    public List<Film> getAllFilms() {
        return filmDao.getAllFilms();
    }

    public boolean filmIsPresent(int filmID) {
        return !filmDao.getFilmByID(filmID).equals(null);
    }

    public Optional<Film> getFilm(int filmID){
        return Optional.of(filmDao.getFilmByID(filmID));
    }
    @Override
    public void addLike(int filmID, int userID) {
        likesDao.userLikeFilm(userID, filmID);
    }
    @Override
    public void removeLike(int filmID, int userID) {
        likesDao.userRemoveLike(userID, filmID);
    }
    @Override
    public List<Film> getMostPopularFilms(int topFilmsAmount) {
        return likesDao.getMostPopularFilms(topFilmsAmount);
    }
     @Override
     public void checkFilmAndUserPresence(int filmID, int userID) {
        if (filmDao.getFilmByID(filmID) == null) {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        } else if (userDao.getUserById(userID) == null) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
    }


}
