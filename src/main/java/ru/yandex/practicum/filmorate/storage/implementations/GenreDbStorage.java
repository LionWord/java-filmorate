package ru.yandex.practicum.filmorate.storage.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorage implements GenreStorage {

    private final GenreDao genreDao;


    @Override
    public Genre getGenre(int genreID) {
        return genreDao.getGenre(genreID);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }
}
