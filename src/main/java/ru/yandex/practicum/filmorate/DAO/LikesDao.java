package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LikesDao {

    Map<String, Integer> userLikeFilm (String userEmail, int filmID);
    boolean userRemoveLike(String userEmail, int filmID);
    Optional<List<String>> allUsersLikedSpecificFilm(int filmID);
}
