package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FailedValidationException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.Messages;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> database = new HashMap<>();

    @Override
    public void addFilm(Film film) {
        if (Validator.isValidFilm(film)) {
            database.put(film.getId(), film);
        } else {
            throw new FailedValidationException(Messages.FAILED_FILM_VALIDATION);
        }
    }

    @Override
    public void deleteFilm(int filmID) {
        if (database.containsKey(filmID)) {
            database.remove(filmID);
        } else {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        }
    }

    @Override
    public void modifyFilm(Film film) {
        if (!Validator.isValidFilm(film)) {
            throw new FailedValidationException(Messages.FAILED_FILM_VALIDATION);
        } else if (!database.containsKey(film.getId())) {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        }
        database.replace(film.getId(), film);
    }
    public List<Film> getAllFilms() {
        return (List<Film>) database.values();
    }

    public Map<Integer, Film> getDatabase() {
        return database;
    }

    public boolean filmIsPresent(int filmID) {
        return database.containsKey(filmID);
    }
    public Film getFilm(int filmID) {
        return database.get(filmID);
    }
}
