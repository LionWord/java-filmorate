package ru.yandex.practicum.filmorate.services;

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
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final Comparator<Film> sortByLikes = (f1, f2) -> {
        if (f1.getLikesCount() < f2.getLikesCount()) {
            return -1;
        } else if (f1.getLikesCount() > f2.getLikesCount()) {
            return 1;
        } else return 0;

    };

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(int filmID, int userID) {
        validatePresence(filmID, userID);
        filmStorage.getFilm(filmID).addLike(userStorage.getUser(userID));
    }
    public void removeLike(int filmID, int userID) {
            validatePresence(filmID, userID);
            filmStorage.getFilm(filmID).removeLike(userStorage.getUser(userID));
    }

    public List<Film> getMostPopularFilms(int topFilmsAmount) {
        if (topFilmsAmount > filmStorage.getAllFilms().size()) {
            throw new InvalidInputException(Messages.TOO_BIG_VALUE);
        }
        return filmStorage.getAllFilms().stream().sorted(sortByLikes).limit(topFilmsAmount).collect(Collectors.toList());
    }

    private void validatePresence(int filmID, int userID) {
        if (!filmStorage.filmIsPresent(filmID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        } else if (!userStorage.userIsPresent(userID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
    }


}
