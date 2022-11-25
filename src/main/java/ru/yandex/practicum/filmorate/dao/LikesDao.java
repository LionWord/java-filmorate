package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LikesDao {

    Map<Integer, Integer> userLikeFilm(int userID, int filmID);
    boolean userRemoveLike(int userID, int filmID);
    List<Film> getMostPopularFilms(int limit);
}
