package ru.yandex.practicum.filmorate.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.LikesDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikesDaoImpl implements LikesDao {

    private final JdbcTemplate jdbcTemplate;
    private final FilmDao filmDao;

    @Override
    public Map<Integer, Integer> userLikeFilm(int userID, int filmID) {
        String sql = "insert into USERS_LIKED_FILM (USER_ID, FILM_ID) values (?,?);" +
                "update FILMS set RATE = RATE + 1 where FILM_ID = (?)";
        try {
            jdbcTemplate.update(sql, userID, filmID, filmID);
        } catch (Exception e) {
            return Map.of();
        }
        return Map.of(userID, filmID);
    }

    @Override
    public boolean userRemoveLike(int userID, int filmID) {
        String sql = "delete from USERS_LIKED_FILM where USER_ID = ? and FILM_ID = ?;" +
                "update FILMS set RATE = RATE - 1 where FILM_ID = (?)";
        try {
            jdbcTemplate.update(sql, userID, filmID, filmID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    @Override
    public List<Film> getMostPopularFilms(int limit) {
        String sql = "select * from FILMS order by RATE desc limit ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> filmDao.makeFilm(rs), limit);
    }
}
