package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InvalidInputException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService implements Rateable{

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final Comparator<Film> sortByLikes = (f1, f2) -> {
        if (f1.getLikesCount() == 0 & f2.getLikesCount() == 0) {
            return f2.getId() - f1.getId();
        }
        return Integer.compare(f2.getLikesCount(), f1.getLikesCount());

    };

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }
    @Override
    public void addLike(int filmID, int userID) {
        log.info("Starting method addLike");
        validateFilmAndUserPresence(filmID, userID);
        filmStorage.getFilm(filmID).addLike(userStorage.getUser(userID));
        log.info("Finishing method addLike");
    }
    @Override
    public void removeLike(int filmID, int userID) {
        log.info("Starting method removeLike");
        validateFilmAndUserPresence(filmID, userID);
        filmStorage.getFilm(filmID).removeLike(userStorage.getUser(userID));
        log.info("Finishing method removeLike");
    }
    @Override
    public List<Film> getMostPopularFilms(int topFilmsAmount) {
        log.info("Starting method getMostPopularFilms");
        if (topFilmsAmount <= 10 || topFilmsAmount <= filmStorage.getAllFilms().size()) {
            log.debug("Returning " + filmStorage.getAllFilms().stream().sorted(sortByLikes).limit(topFilmsAmount).collect(Collectors.toList()));
            return filmStorage.getAllFilms().stream().sorted(sortByLikes).limit(topFilmsAmount).collect(Collectors.toList());
        }
        if (topFilmsAmount > filmStorage.getAllFilms().size()) {
            throw new InvalidInputException(Messages.TOO_BIG_VALUE);
        }
        return filmStorage.getAllFilms().stream().sorted(sortByLikes).limit(topFilmsAmount).collect(Collectors.toList());
    }
     @Override
     public void validateFilmAndUserPresence(int filmID, int userID) {
        log.debug("Validating presence of film ID=" + filmID + " and user ID=" + userID);
        if (!filmStorage.filmIsPresent(filmID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        } else if (userStorage.userIsPresent(userID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
        log.debug("Presence successfully validated");
    }


}
