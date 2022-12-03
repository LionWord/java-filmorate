package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.controllers.GenresController;
import ru.yandex.practicum.filmorate.controllers.MpaController;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmorateApplicationTests {
    private final UserController userController;
    private final FilmController filmController;
    private final MpaController mpaController;
    private final GenresController genresController;
    private final JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void prepareDatabaseForTests() {
        String sql = "delete " +
                "from FRIENDS; " +
                " " +
                "delete " +
                "from FRIENDSHIP_REQUESTS; " +
                " " +
                "delete " +
                "from GENRES_OF_FILMS; " +
                " " +
                "delete " +
                "from USERS_LIKED_FILM; " +
                " " +
                "delete " +
                "from FILMS; " +
                " " +
                "delete " +
                "from USER_INFO; " +
                " " +
                "ALTER TABLE USER_INFO " +
                "    ALTER COLUMN ID " +
                "        RESTART WITH 1; " +
                " " +
                "ALTER TABLE FILMS " +
                "    ALTER COLUMN FILM_ID " +
                "        RESTART WITH 1; " +
                " " +
                "ALTER TABLE GENRES " +
                "    ALTER COLUMN GENRE_ID " +
                "        RESTART WITH 1;" +
                "insert into USER_INFO (EMAIL, LOGIN, USER_NAME, BIRTHDAY) " +
                "values ('mail@yandex.ru', 'dolore', 'est adipisicing', '1976-09-20'), " +
                "       ('fail@ya.ru', 'olotor', 'disgustin', '1973-01-01'), " +
                "       ('trail@rambler.ru', 'jack', 'olddad', '1951-02-02'); " +
                " " +
                "insert into FILMS (FILM_NAME, RELEASE_DATE, DURATION, DESCRIPTION, MPA_RATING_ID) " +
                "values ('New film', '1999-04-30', 120, 'New film about friends', 3), " +
                "       ('Opop', '1998-04-30', 150, 'On opop', 2), " +
                "       ('Coco', '1997-04-30', 110, 'On coco', 1);";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindUserById() {

        Optional<User> userOptional = Optional.of(userController.getUser(1));

        Assertions.assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testGetUserByIdGettingCorrectParams() {
        Optional<User> userOptional = Optional.of(userController.getUser(1));
        Assertions.assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user -> Assertions.assertThat(user).hasFieldOrPropertyWithValue("id", 1))
                .hasValueSatisfying(user -> Assertions.assertThat(user).hasFieldOrPropertyWithValue("name", "est adipisicing"))
                .hasValueSatisfying(user -> Assertions.assertThat(user).hasFieldOrPropertyWithValue("login", "dolore"))
                .hasValueSatisfying(user -> Assertions.assertThat(user).hasFieldOrPropertyWithValue("email", "mail@yandex.ru"))
                .hasValueSatisfying(user -> Assertions.assertThat(user).hasFieldOrPropertyWithValue("birthday", LocalDate.of(1976, 9, 20)));
    }

    @Test
    public void getAllUsers() {
        List<User> usersListOptional = userController.getAllUsers();
        Assertions.assertThat(usersListOptional.size()).isEqualTo(3);
    }

    @Test
    public void addUser() {
        User testUser = User.builder().name("a").email("f@a.ru").login("yes").name("Foo").birthday(LocalDate.of(1976, 9, 20)).build();
        userController.addUser(testUser);
        List<User> usersListOptional = userController.getAllUsers();
        Assertions.assertThat(usersListOptional.size()).isEqualTo(4);
    }

    @Test
    public void modifyUser() {
        User testUser = User.builder()
                .name("a")
                .email("f@a.ru")
                .login("yes")
                .name("Foo")
                .birthday(LocalDate.of(1976, 9, 20))
                .build();
        userController.addUser(testUser);
        testUser.setLogin("no");
        userController.updateUser(testUser);
        Assertions.assertThat(userController.getUser(4).getLogin()).isEqualTo("no");
    }

    @Test
    public void befriendUser() {
        userController.befriendUser(1, 2);
        List<User> users = userController.getUserFriends(1).get();
        Assertions.assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void unfriendUser() {
        userController.befriendUser(1, 2);
        int size = userController.getUserFriends(1).get().size();
        userController.unfriendUser(1, 2);
        int sizeTwo = userController.getUserFriends(1).get().size();
        Assertions.assertThat(size).isNotEqualTo(sizeTwo);
    }

    @Test
    public void getCommonFriends() {
        userController.befriendUser(1, 2);
        userController.befriendUser(3, 2);
        List<User> commonFriends = userController.getCommonFriends(1, 3).get();
        Assertions.assertThat(commonFriends).contains(userController.getUser(2));
    }

    @Test
    public void testFindFilmById() {
        Optional<Film> filmOptional = Optional.of(filmController.getFilm(1));
        Assertions.assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void getAllFilms() {
        List<Film> filmsListOptional = filmController.getAllFilms();
        Assertions.assertThat(filmsListOptional.size()).isEqualTo(3);
    }

    @Test
    public void addFilm() {
        Film film = Film.builder()
                .name("ahaha")
                .description("ohoho")
                .releaseDate(Date.valueOf("1976-09-20"))
                .duration(122)
                .mpa(mpaController.getMPA(1))
                .build();
        filmController.addFilm(film);
        Assertions.assertThat(filmController.getAllFilms().size()).isEqualTo(4);
    }

    @Test
    public void updateFilm() {
        Film film = Film.builder()
                .name("ahaha")
                .description("ohoho")
                .releaseDate(Date.valueOf("1976-09-20"))
                .duration(122)
                .mpa(mpaController.getMPA(1))
                .id(1)
                .build();
        filmController.updateFilm(film);
        Assertions.assertThat(filmController.getFilm(1).getName()).isEqualTo("ahaha");
    }

    @Test
    public void likeFilm() {
        filmController.likeFilm(1, 1);
        Assertions.assertThat(filmController.getMostLikedFilms(10).get(0))
                .isEqualTo(filmController.getFilm(1));
    }

    @Test
    public void dislikeFilm() {
        filmController.likeFilm(1, 1);
        int rateOne = filmController.getFilm(1).getRate();
        filmController.unlikeFilm(1, 1);
        int rateTwo = filmController.getFilm(1).getRate();
        Assertions.assertThat(rateOne).isNotEqualTo(rateTwo);
    }

    @Test
    public void getGenre() {
        Genre genre = genresController.getGenre(1);
        Assertions.assertThat(genre.getName()).isEqualTo("Комедия");
    }

    @Test
    public void getAllGenres() {
        Assertions.assertThat(genresController.getAllGenres().size()).isEqualTo(6);
    }

    @Test
    public void getMpa() {
        MPA mpa = mpaController.getMPA(1);
        Assertions.assertThat(mpa.getName()).isEqualTo("G");
    }

    @Test
    public void getAllMpa() {
        Assertions.assertThat(mpaController.getAllMPA().size()).isEqualTo(5);
    }

}