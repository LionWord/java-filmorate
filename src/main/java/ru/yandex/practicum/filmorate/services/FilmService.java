package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.DAO.FilmDao;
import ru.yandex.practicum.filmorate.DAO.LikesDao;
import ru.yandex.practicum.filmorate.DAO.UserDao;
import ru.yandex.practicum.filmorate.DAO.implementations.FilmDaoImpl;
import ru.yandex.practicum.filmorate.DAO.implementations.LikesDaoImpl;
import ru.yandex.practicum.filmorate.DAO.implementations.UserDaoImpl;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

    public void addFilm(Film film) {
        filmDao.addFilm(film);
    }

    public void deleteFilm(int filmID) {
        filmDao.removeFilmByID(filmID);
    }

    public void modifyFilm(Film film) {
        filmDao.updateFilm(film);
    }

    public Optional<List<Film>> getAllFilms() {
        return filmDao.getAllFilms();
    }

    public boolean filmIsPresent(int filmID) {
        return filmDao.getFilmByID(filmID).isPresent();
    }

    public Optional<Film> getFilm(int filmID){
        return filmDao.getFilmByID(filmID);
    }

    @Override
    public void addLike(int filmID, int userID) {
        log.info("Starting method addLike");
        likesDao.userLikeFilm(userID, filmID);
        log.info("Finishing method addLike");
    }
    @Override
    public void removeLike(int filmID, int userID) {
        log.info("Starting method removeLike");
        likesDao.userRemoveLike(userID, filmID);
        log.info("Finishing method removeLike");
    }
    @Override
    public List<Film> getMostPopularFilms(int topFilmsAmount) {
        log.info("Starting method getMostPopularFilms");
        return likesDao.getMostPopularFilms(topFilmsAmount);
    }
     @Override
     public void checkFilmAndUserPresence(int filmID, int userID) {
        log.debug("Validating presence of film ID=" + filmID + " and user Email=" + userID);
        if (filmDao.getFilmByID(filmID).isEmpty()) {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        } else if (userDao.getUserById(userID).isEmpty()) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
        log.debug("Presence successfully validated");
    }


}
