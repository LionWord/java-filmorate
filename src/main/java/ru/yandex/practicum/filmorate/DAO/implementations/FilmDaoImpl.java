package ru.yandex.practicum.filmorate.DAO.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.FilmDao;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FilmDaoImpl implements FilmDao {

    final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film film) {
        String sql = "insert into FILMS (FILM_NAME, RELEASE_DATE, DURATION, DESCRIPTION, MPA_RATING_ID) values(?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "FILM_ID" });
            ps.setString(1, film.getName());
            ps.setDate(2, film.getReleaseDate());
            ps.setInt(3, film.getDuration());
            ps.setString(4, film.getDescription());
            ps.setInt(5, film.getMpa().get("id"));
            return ps;
        }, keyHolder);
            film.setId(keyHolder.getKey().intValue());
            return film;
        }

    @Override
    public Film updateFilm(Film film) {
        String sql = "update FILMS " +
                "set FILM_NAME = ?, RELEASE_DATE = ?, DURATION = ?, DESCRIPTION = ?, MPA_RATING_ID = ? " +
                "where FILM_ID = ?";
        try {
            jdbcTemplate.update(sql,
                    film.getName(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getDescription(),
                    film.getMpa().get("id"),
                    film.getId());
        } catch (Exception e) {
            return null;
        }
        return film;
    }

    @Override
    public boolean removeFilmByID(int filmID) {
        if (getFilmByID(filmID).isEmpty()) {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        }
        String sql = "delete from FILMS where FILM_ID = ?";
        try {
            jdbcTemplate.update(sql, filmID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<Film> getFilmByID(int filmID) {
        String sql = "select * from FILMS " +
                "where FILM_ID = ?";
       try {
           return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ((rs, rowNum) -> makeFilm(rs)), filmID));
       } catch (Exception e) {
           throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
       }

    }
    @Override
    public Film makeFilm(ResultSet rs) throws SQLException {
        return Film.builder()
                .name(rs.getString("FILM_NAME"))
                .releaseDate(rs.getDate("RELEASE_DATE"))
                .id(rs.getInt("FILM_ID"))
                .duration(rs.getInt("DURATION"))
                .description(rs.getString("DESCRIPTION"))
                .mpa(Map.of("id", rs.getInt(("MPA_RATING_ID"))))
                .build();
    }

    @Override
    public Optional<List<Film>> getAllFilms() {
        String sql = "select * from FILMS";
        return Optional.of(jdbcTemplate.query(sql, ((rs, rowNum) -> makeFilm(rs))));
    }

}
