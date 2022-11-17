package ru.yandex.practicum.filmorate.DAO.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.FilmDao;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.Messages;
import ru.yandex.practicum.filmorate.utils.RatingMPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class FilmDaoImpl implements FilmDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film film) {
        String sql = "insert into FILMS (FILM_NAME, RELEASE_DATE, DURATION, DESCRIPTION, MPA_RATING_ID) values(?,?,?,?,?)";
        try {
            jdbcTemplate.update(sql,
                    film.getName(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getDescription(),
                    film.getRatingMPA());
            } catch (Exception e) {
            return null;
        }
            return film;
        }

    @Override
    public boolean removeFilm(Film film) {
        return removeFilmByID(film.getId());
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
                    film.getRatingMPA(),
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
        String sql = "select f.* from FILMS as f " +
                "JOIN MPA_RATING as m ON f.MPA_RATING_ID=m.MPA_RATING_ID " +
                "where f.FILM_ID = ?";
        jdbcTemplate.queryForObject(sql, ((rs, rowNum) -> makeFilm(rs)), filmID);

        return Optional.empty();
    }
    @Override
    public Film makeFilm(ResultSet rs) throws SQLException {
        return Film.builder()
                .name(rs.getString("FILM_NAME"))
                .releaseDate(rs.getDate("RELEASE_DATE").toLocalDate())
                .id(rs.getInt("FILM_ID"))
                .duration(rs.getInt("DURATION"))
                .description(rs.getString("DESCRIPTION"))
                .ratingMPA(RatingMPA.valueOf(rs.getString("m.RATING_NAME")))
                .build();
    }

}
