package ru.yandex.practicum.filmorate.DAO.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.LikesDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class LikesDaoImpl implements LikesDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public LikesDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<String, Integer> userLikeFilm(String userEmail, int filmID) {
        String sql = "insert into USERS_LIKED_FILM (USER_EMAIL, FILM_ID) values (?,?)";
        try {
            jdbcTemplate.update(sql, userEmail, filmID);
        } catch (Exception e) {
            return Map.of();
        }
        return Map.of(userEmail, filmID);
    }

    @Override
    public boolean userRemoveLike(String userEmail, int filmID) {
        String sql = "delete from USERS_LIKED_FILM where USER_EMAIL = ? and FILM_ID = ?";
        try {
            jdbcTemplate.update(sql, userEmail, filmID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<List<String>> allUsersLikedSpecificFilm(int filmID) {
        String sql = "select USER_EMAIL from USERS_LIKED_FILM where FILM_ID = ?";
        return Optional.of(jdbcTemplate.queryForList(sql, String.class, filmID));
    }
}
