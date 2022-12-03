package ru.yandex.practicum.filmorate.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getGenresByID(Integer... genreID) {
        String sql = "select * from GENRES where GENRE_ID = ?";
        int[] types = new int[]{Types.INTEGER, Types.VARCHAR};
        try {
            return jdbcTemplate.queryForList(sql, genreID, types, Genre.class);
        } catch (Exception e) {
            throw new NoSuchEntryException(Messages.INVALID_GENRE);
        }
    }

    @Override
    public Genre getGenre(int genreID) {
        String sql = "select * from GENRES where GENRE_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ((rs, rowNum) -> makeGenre(rs)), genreID);
        } catch (Exception e) {
            throw new NoSuchEntryException(Messages.INVALID_GENRE);
        }
    }

    @Override
    public List<Integer> getGenresOfFilm(int filmID) {
        String sql = "select GENRE_ID from GENRES_OF_FILMS where FILM_ID = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, filmID);
    }

    public List<Genre> getAllGenres() {
        String sql = "select * from GENRES";
        return jdbcTemplate.query(sql, ((rs, rowNum) -> makeGenre(rs)));
    }

    @Override
    public void connectGenreAndFilm(Film film) {
        String sql = "insert into GENRES_OF_FILMS (FILM_ID, GENRE_ID) values (?,?)";
        for (int i = 0; i < film.getGenres().size(); i++) {
            jdbcTemplate.update(sql, film.getId(), film.getGenres().get(i).getId());
        }
    }

    @Override
    public void disconnectGenreAndFilm(Film film) {
        String sql = "delete from GENRES_OF_FILMS where FILM_ID = ?";
        jdbcTemplate.update(sql, film.getId());
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("GENRE_ID"))
                .name(rs.getString("GENRE_NAME"))
                .build();
    }
}
