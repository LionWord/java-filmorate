package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.FailedValidationException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.IdAssigner;
import ru.yandex.practicum.filmorate.utils.Messages;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Repository
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> database = new HashMap<>();

    @Override
    public void addFilm(Film film) {
        log.info("Starting method addFilm");
            film.setId(IdAssigner.getFilmID());
            IdAssigner.increaseFilmID();
            database.put(film.getId(), film);
            log.info("Finishing method deleteFilm");
    }

    @Override
    public void deleteFilm(int filmID) {
        log.info("Starting method deleteFilm");
        if (database.containsKey(filmID)) {
            log.info("Finishing method deleteFilm");
            database.remove(filmID);
        } else {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        }
    }

    @Override
    public void modifyFilm(Film film) {
        log.info("Starting method modifyFilm");
        database.replace(film.getId(), film);
        log.info("Finishing method modifyFilm");
    }

    public List<Film> getAllFilms() {
        log.info("Starting method getAllFilms");
        if (database.isEmpty()) {
            log.info("Database is empty. Returning empty ArrayList");
            return new ArrayList<>();
        } else {
            log.info("Returned values: " + database.values());
            log.info("Finished method getAllFilms");
            return new ArrayList<>(database.values());
        }
    }

    public Map<Integer, Film> getDatabase() {
        log.info("Getting database");
        return database;
    }

    public boolean filmIsPresent(int filmID) {
        log.debug("Checking if film is present");
        return database.containsKey(filmID);
    }

    public Film getFilm(int filmID) {
        log.info("Getting film");
        log.info("Method getFilm returned this value - " + database.get(filmID));
        return database.get(filmID);

    }

}
