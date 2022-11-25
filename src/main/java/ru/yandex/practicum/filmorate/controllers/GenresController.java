package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenresController {

    private final GenreDao genreDao;

    @GetMapping({"{id}"})
    public Genre getGenre(@PathVariable(value = "id") int genreID) {
        return genreDao.getGenre(genreID);
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

}
