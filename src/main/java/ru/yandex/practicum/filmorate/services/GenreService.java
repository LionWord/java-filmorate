package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreService {

    private final GenreStorage genreStorage;

    public Genre getGenre(int genreID) {
        return genreStorage.getGenre(genreID);
    }

    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }

}
