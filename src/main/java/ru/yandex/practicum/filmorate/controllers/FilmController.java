package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.FailedValidationException;
import ru.yandex.practicum.filmorate.exceptions.InvalidInputException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.FilmService;
import ru.yandex.practicum.filmorate.services.Rateable;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.utils.Messages;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final FilmStorage filmStorage;
    private final Rateable filmService;

    @Autowired
    public FilmController(InMemoryFilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @GetMapping({"{id}"})
    public Film getFilm(@PathVariable(value = "id") int filmID) {
        if (!filmStorage.getDatabase().containsKey(filmID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        }
        return filmStorage.getFilm(filmID);
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        if (!Validator.isValidFilm(film)) {
            throw new FailedValidationException(Messages.FAILED_FILM_VALIDATION);
        } else if (filmStorage.getDatabase().containsValue(film)) {
            throw new AlreadyExistsException(Messages.FILM_ALREADY_EXISTS);
        }
        filmStorage.addFilm(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws InvalidInputException {
        if (!Validator.isValidFilm(film)) {
            throw new FailedValidationException(Messages.FAILED_FILM_VALIDATION);
        } else if (!filmStorage.getDatabase().containsKey(film.getId())) {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        }
        filmStorage.modifyFilm(film);
        return film;
    }

    @PutMapping("{id}/like/{userId}")
    public void likeFilm(@PathVariable(value = "id") int filmID,
                         @PathVariable(value = "userId") int userID) {
        filmService.checkFilmAndUserPresence(filmID, userID);
        filmService.addLike(filmID, userID);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void unlikeFilm(@PathVariable(value = "id") int filmID,
                           @PathVariable(value = "userId") int userID) {
        filmService.checkFilmAndUserPresence(filmID, userID);
        filmService.removeLike(filmID, userID);
    }

    @GetMapping("/popular")
    public List<Film> getMostLikedFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getMostPopularFilms(count);
    }
}
