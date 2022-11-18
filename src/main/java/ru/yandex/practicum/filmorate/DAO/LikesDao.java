package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LikesDao {

    public Map<Integer, Integer> userLikeFilm(int userID, int filmID);
    public boolean userRemoveLike(int userID, int filmID);
    public Optional<List<Integer>> allUsersLikedSpecificFilm(int filmID);
    public List<Film> getMostPopularFilms(int limit);
}
