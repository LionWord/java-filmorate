package ru.yandex.practicum.filmorate.DAO.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.GenreDao;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Component
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getGenresByID(Integer ... genreID) {
        String sql = "select * from GENRES where GENRE_ID = ?";
        int[] types = new int[] {Types.INTEGER, Types.VARCHAR};
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
    public List<Genre> getGenresOfFilm(int filmID) {
        String sql = "select * from GENRES as g " +
                "left join GENRES_OF_FILMS as gof on gof.GENRE_ID=g.GENRE_ID " +
                "where gof.FILM_ID = ?";
        return jdbcTemplate.queryForList(sql, Genre.class, filmID);
    }
    public List<Genre> getAllGenres() {
        String sql = "select * from GENRES";
        return jdbcTemplate.query(sql, ((rs, rowNum) -> makeGenre(rs)));
    }
    private Genre makeGenre(ResultSet rs) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("GENRE_ID"))
                .name(rs.getString("GENRE_NAME"))
                .build();
    }
}