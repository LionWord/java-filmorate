package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FilmorateApplicationTests {

    @Test
    void shouldReturnFalseIfUserIsValid() {
        User user = User.builder()
                .id(1)
                .birthday(LocalDate.of(1984, 12, 21))
                .email("ilovejava@yandex.ru")
                .name("Ivan")
                .login("Zeliboba")
                .build();
        assertFalse(UserController.isInvalidUserInput(user));
    }

    @Test
    void shouldReturnTrueIfEmailDontContainsAtSign() {
        User user = User.builder()
                .id(2)
                .birthday(LocalDate.of(1984, 12, 21))
                .email("azaza")
                .name("Andrew")
                .login("I_HATE_VALID_EMAILS")
                .build();
        assertTrue(UserController.isInvalidUserInput(user));
    }

    @Test
    void shouldReturnTrueIfEmailIsEmpty() {
        User user = User.builder()
                .id(4)
                .birthday(LocalDate.of(1999, 4, 3))
                .email("")
                .name("Eugen Blank")
                .login("E.Blank")
                .build();
        UserController.isInvalidUserInput(user);
    }

    @Test
    void shouldReturnTrueIfLoginIsEmpty() {
        User user = User.builder()
                .id(6)
                .birthday(LocalDate.of(1991, 1, 1))
                .email("iam@login.hater")
                .name("Olologin")
                .login("")
                .build();
        assertTrue(UserController.isInvalidUserInput(user));
    }

    @Test
    void shouldReturnTrueIfLoginContainsSpaces() {
        User user = User.builder()
                .id(5)
                .birthday(LocalDate.of(1947, 1, 8))
                .email("space@oddity.pun")
                .name("David Bowie")
                .login("Ground control to major Tom")
                .build();
        assertTrue(UserController.isInvalidUserInput(user));
    }

    @Test
    void shouldReturnTrueIfUserIsFromTheFuture() {
        User user = User.builder()
                .id(3)
                .birthday(LocalDate.of(2029, 7, 11))
                .email("clothes@boots.motorcycle")
                .name("T800")
                .login("IllBeBack")
                .build();
        assertTrue(UserController.isInvalidUserInput(user));
    }

    @Test
    void shouldReturnFalseIfFilmIsValid() {
        Film film = Film.builder()
                .id(1)
                .name("Godfather")
                .description("Stylish guys kill each other")
                .releaseDate(LocalDate.of(1972, 3, 14))
                .duration(175)
                .build();
        assertFalse(FilmController.isInvalidFilmInput(film));
    }

    @Test
    void shouldReturnTrueIfFilmIsTooOld() {
        Film film = Film.builder()
                .id(2)
                .name("Stoneage")
                .description("Can't beat Lumiere brothers.")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(60)
                .build();
        assertTrue(FilmController.isInvalidFilmInput(film));
    }

    @Test
    void shouldReturnTrueIfDescriptionLengthIsMoreThan200() {
        Film film = Film.builder()
                .id(3)
                .name("Mr Trololo")
                .description("Ahhhh, ya ya yaaah,\n" +
                        "ya ya yah yah ya yaaah.\n" +
                        "Oh oh oh oh oooh, oh ya yah,\n" +
                        "ya ya yah yah ya yah.\n" +
                        "\n" +
                        "Ye ye ye ye ye, ye ye yeh, ye ye yeh.\n" +
                        "Oh oh oh oh oooh.\n" +
                        "Ye ye ye ye ye, ye ye yeh, ye ye yeh.\n" +
                        "Oh oh oh oh oooh, lololol.\n" +
                        "Oh oh oooh oooh, la lah.\n" +
                        "Na na na na nah na na nah na na nah na na nah na na nah.\n" +
                        "Na na na na nan na na nan, na na nah,\n" +
                        "na na na na nah.")
                .releaseDate(LocalDate.of(1966, 1, 1))
                .duration(3)
                .build();
        assertTrue(FilmController.isInvalidFilmInput(film));
    }

    @Test
    void shouldReturnTrueIfFilmDoesNotContainName() {
        Film film = Film.builder()
                .id(4)
                .name("")
                .description("Hush! I'm anonymous!")
                .releaseDate(LocalDate.of(1999, 12, 27))
                .duration(60)
                .build();
        assertTrue(FilmController.isInvalidFilmInput(film));
    }

    @Test
    void shouldReturnTrueIfFilmDurationIsNegative() {
        Film film = Film.builder()
                .id(5)
                .name("Pessimist")
                .description("I'm lesser than zero! What a shame!")
                .releaseDate(LocalDate.of(1999, 12, 27))
                .duration(-1)
                .build();
        assertTrue(FilmController.isInvalidFilmInput(film));
    }

    @Test
    void shouldReturnTrueIfFilmDurationIsZero() {
        Film film = Film.builder()
                .id(6)
                .name("Smashing Pumpkins")
                .description("I'm your lover, i'm your zero")
                .releaseDate(LocalDate.of(1996, 4, 23))
                .duration(0)
                .build();
        assertTrue(FilmController.isInvalidFilmInput(film));
    }
}
