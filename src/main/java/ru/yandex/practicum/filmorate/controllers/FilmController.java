package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.InvalidInputException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private HashMap<Integer, Film> database = new HashMap<>();
    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList<>(database.values());
    }
    @PostMapping
    public Film addFilm(@RequestBody Film film) throws InvalidInputException, AlreadyExistsException {
        if (isInvalidFilmInput(film)) {
            log.error("Invalid input.");
            throw new InvalidInputException("Invalid input.");
        }
        if (database.containsKey(film.getId())) {
            log.debug("Film already exists.");
            throw new AlreadyExistsException("Film already exists.");
        } else {
            database.put(film.getId(), film);
            log.info("Film entry " + film.getName() + " succesfully added.");
            return film;
        }
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws InvalidInputException {
        if (isInvalidFilmInput(film)) {
            log.error("Invalid input.");
            throw new InvalidInputException("Invalid input.");
        }
            database.put(film.getId(), film);
            log.info("Film entry" + film.getName() + " succesfully updated.");
            return film;
    }

    public boolean isInvalidFilmInput(Film film) {
        return film.getName().isEmpty()
                || film.getDescription().length() > 200
                || film.getDuration().isNegative()
                || film.getDuration().isZero()
                || film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28));
    }
}
