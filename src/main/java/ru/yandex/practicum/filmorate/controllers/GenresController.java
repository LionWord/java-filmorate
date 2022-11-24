package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.DAO.GenreDao;
import ru.yandex.practicum.filmorate.DAO.MpaDao;
import ru.yandex.practicum.filmorate.DAO.implementations.GenreDaoImpl;
import ru.yandex.practicum.filmorate.DAO.implementations.MpaDaoImpl;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenresController {

    private final GenreDao genreDao;

    @Autowired
    public GenresController(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @GetMapping({"{id}"})
    public Genre getGenre(@PathVariable(value = "id") int genreID) {
        return genreDao.getGenre(genreID);
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

}
