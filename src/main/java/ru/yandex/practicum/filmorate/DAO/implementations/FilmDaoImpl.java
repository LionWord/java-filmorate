package ru.yandex.practicum.filmorate.DAO.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.FilmDao;
import ru.yandex.practicum.filmorate.DAO.GenreDao;
import ru.yandex.practicum.filmorate.DAO.MpaDao;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;
    private final MpaDao mpaDao;
    private final GenreDao genreDao;
    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate, MpaDao mpaDao, GenreDao genreDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaDao = mpaDao;
        this.genreDao = genreDao;
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
            ps.setInt(5, film.getMpa().getId());
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
                    film.getMpa().getId(),
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
    public Film setFilmGenres(Film film) {
            String genreSql = "insert into GENRES_OF_FILMS (FILM_ID, GENRE_ID) " +
                    "values (?,?)";
            List<Genre> genres = new ArrayList<>();
            for (Genre g : film.getGenres()) {
                jdbcTemplate.update(genreSql, film.getId(), g.getId());
                g.setName(genreDao.getGenre(g.getId()).getName());
                genres.add(g);
            }
            film.setGenres(genres);
            return film;
    }

    @Override
    public Optional<List<Film>> getAllFilms() {
        String sql = "select * from FILMS";
        return Optional.of(jdbcTemplate.query(sql, ((rs, rowNum) -> makeFilm(rs))));
    }

    @Override
    public Film makeFilm(ResultSet rs) throws SQLException {
        MPA mpa = mpaDao.getMpaByID(rs.getInt("MPA_RATING_ID"));
        List<Genre> genresList;
        try {
            genresList = genreDao.getGenresOfFilm(rs.getInt("FILM_ID"));
        } catch (Exception e) {
            genresList = List.of();
        }
        return Film.builder()
                .name(rs.getString("FILM_NAME"))
                .releaseDate(rs.getDate("RELEASE_DATE"))
                .id(rs.getInt("FILM_ID"))
                .duration(rs.getInt("DURATION"))
                .description(rs.getString("DESCRIPTION"))
                .mpa(mpa)
                .genres(genresList)
                .build();
    }


}
