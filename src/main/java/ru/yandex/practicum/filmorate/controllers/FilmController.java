package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FailedValidationException;
import ru.yandex.practicum.filmorate.exceptions.InvalidInputException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.FilmService;
import ru.yandex.practicum.filmorate.services.UserService;
import ru.yandex.practicum.filmorate.utils.Messages;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private final UserService userService;

    @Autowired
    public FilmController(FilmService filmService, UserService userService) {
        this.filmService = filmService;
        this.userService = userService;
    }

    @GetMapping
    public Optional<List<Film>> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping({"{id}"})
    public Optional<Film> getFilm(@PathVariable(value = "id") int filmID) {
        if (!filmService.filmIsPresent(filmID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        }
        return filmService.getFilm(filmID);
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        if (!Validator.isValidFilm(film)) {
            throw new FailedValidationException(Messages.FAILED_FILM_VALIDATION);
        }
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws InvalidInputException {
        if (!Validator.isValidFilm(film)) {
            throw new FailedValidationException(Messages.FAILED_FILM_VALIDATION);
        } else if (!filmService.filmIsPresent(film.getId())) {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        }
        return filmService.modifyFilm(film);
    }

    @PutMapping("{id}/like/{userId}")
    public void likeFilm(@PathVariable(value = "id") int filmID,
                         @PathVariable(value = "userId") int userID) {
        if (filmService.filmIsPresent(filmID) & userService.userIsPresent(userID)) {
            filmService.addLike(filmID, userID);
        } else {
            throw new NoSuchEntryException(Messages.TRY_ANOTHER_ID);
        }
    }

    @DeleteMapping("{id}/like/{userId}")
    public void unlikeFilm(@PathVariable(value = "id") int filmID,
                           @PathVariable(value = "userId") int userID) {

        if (filmService.filmIsPresent(filmID) & userService.userIsPresent(userID)) {
            filmService.removeLike(filmID, userID);
        } else {
            throw new NoSuchEntryException(Messages.TRY_ANOTHER_ID);
        }
    }

    @GetMapping("/popular")
    public List<Film> getMostLikedFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getMostPopularFilms(count);
    }


}
