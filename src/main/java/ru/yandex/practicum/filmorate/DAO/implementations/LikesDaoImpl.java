package ru.yandex.practicum.filmorate.DAO.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.FilmDao;
import ru.yandex.practicum.filmorate.DAO.LikesDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class LikesDaoImpl implements LikesDao {

    private final JdbcTemplate jdbcTemplate;
    private final FilmDao filmDao;

    @Autowired
    public LikesDaoImpl(JdbcTemplate jdbcTemplate, FilmDao filmDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmDao = filmDao;
    }

    @Override
    public Map<Integer, Integer> userLikeFilm(int userID, int filmID) {
        String sql = "insert into USERS_LIKED_FILM (USER_ID, FILM_ID) values (?,?)";
        try {
            jdbcTemplate.update(sql, userID, filmID);
        } catch (Exception e) {
            return Map.of();
        }
        return Map.of(userID, filmID);
    }

    @Override
    public boolean userRemoveLike(int userID, int filmID) {
        String sql = "delete from USERS_LIKED_FILM where USER_ID = ? and FILM_ID = ?";
        try {
            jdbcTemplate.update(sql, userID, filmID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<List<Integer>> allUsersLikedSpecificFilm(int filmID) {
        String sql = "select USER_ID from USERS_LIKED_FILM where FILM_ID = ?";
        return Optional.of(jdbcTemplate.queryForList(sql, Integer.class, filmID));
    }

    @Override
    public List<Film> getMostPopularFilms(int limit) {
        String sql = "select * from FILMS order by RATE desc limit ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> filmDao.makeFilm(rs), limit);


    }
}
