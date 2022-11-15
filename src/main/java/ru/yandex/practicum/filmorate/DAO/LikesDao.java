package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface LikesDao {

    Map<User, Film> userLikeFilm (int userID, int filmID);

}
