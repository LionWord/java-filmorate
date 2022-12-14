package ru.yandex.practicum.filmorate.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.MpaDao;
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
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;
    private final MpaDao mpaDao;
    private final GenreDao genreDao;

    @Override
    public Film addFilm(Film film) {
        String sql = "insert into FILMS (FILM_NAME, RELEASE_DATE, DURATION, DESCRIPTION, MPA_RATING_ID, RATE) values(?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"FILM_ID"});
            ps.setString(1, film.getName());
            ps.setDate(2, film.getReleaseDate());
            ps.setInt(3, film.getDuration());
            ps.setString(4, film.getDescription());
            ps.setInt(5, film.getMpa().getId());
            ps.setInt(6, film.getRate());
            return ps;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
        if (film.getGenres() != null) {
            genreDao.connectGenreAndFilm(film);
            return setFilmGenres(film);
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = "update FILMS " +
                "set FILM_NAME = ?, RELEASE_DATE = ?, DURATION = ?, DESCRIPTION = ?, MPA_RATING_ID = ?, RATE = ? " +
                "where FILM_ID = ?";
        try {
            jdbcTemplate.update(sql,
                    film.getName(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getDescription(),
                    film.getMpa().getId(),
                    film.getRate(),
                    film.getId());
        } catch (Exception e) {
            return null;
        }
        if (returnUpdatedGenresList(film).containsKey(1)) {
            film.setGenres(returnUpdatedGenresList(film).get(1));
            genreDao.disconnectGenreAndFilm(film);
            if (film.getGenres() == null) {
                return film;
            }
            genreDao.connectGenreAndFilm(film);
            return setFilmGenres(film);
        }

        return film;
    }

    @Override
    public boolean removeFilmByID(int filmID) {
        if (getFilmByID(filmID) == null) {
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
    public Film getFilmByID(int filmID) {
        String sql = "select * from FILMS " +
                "where FILM_ID = ?";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(sql, ((rs, rowNum) -> makeFilm(rs)), filmID);
            if (genreDao.getGenresOfFilm(filmID) != null) {
                film.setGenres(makeGenresList(filmID));
            }
        } catch (Exception e) {
            throw new NoSuchEntryException(Messages.NO_SUCH_FILM);
        }
        return film;
    }

    @Override
    public Film setFilmGenres(Film film) {
        List<Genre> genres = new ArrayList<>();
        for (Genre g : film.getGenres()) {
            g.setName(genreDao.getGenre(g.getId()).getName());
            genres.add(g);
        }
        film.setGenres(genres);
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "select * from FILMS";
        return jdbcTemplate.query(sql, ((rs, rowNum) -> makeFilm(rs)));
    }

    private Map<Integer, List<Genre>> returnUpdatedGenresList(Film film) {
        List<Genre> oldGenres = getFilmByID(film.getId()).getGenres();
        List<Genre> newGenres = film.getGenres();
        if (oldGenres != newGenres) {
            if (newGenres == null) {
                return Map.of(1, List.of());
            }
            newGenres = newGenres.stream().distinct().collect(Collectors.toList());
            return Map.of(1, newGenres);
        }

        return Map.of(0, Objects.requireNonNullElseGet(oldGenres, List::of));
    }

    private List<Genre> makeGenresList(int filmID) {
        List<Integer> genresID = genreDao.getGenresOfFilm(filmID);
        List<Genre> genresInstances = new ArrayList<>();
        for (Integer i : genresID) {
            genresInstances.add(genreDao.getGenre(i));
        }
        return genresInstances;
    }

    @Override
    public Film makeFilm(ResultSet rs) throws SQLException {
        MPA mpa = mpaDao.getMpaByID(rs.getInt("MPA_RATING_ID"));
        List<Genre> genresList;
        try {
            genresList = makeGenresList(rs.getInt("FILM_ID"));
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
                .rate(rs.getInt("RATE"))
                .build();
    }


}
