package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OldFilmorateApplicationTests {

    @Test
    void shouldReturnTrueIfUserIsValid() {
        User user = User.builder()
                .id(1)
                .birthday(LocalDate.of(1984, 12, 21))
                .email("ilovejava@yandex.ru")
                .username("Ivan")
                .login("Zeliboba")
                .build();
        assertTrue(Validator.isValidUser(user));
    }

    @Test
    void shouldReturnFalseIfEmailDontContainsAtSign() {
        User user = User.builder()
                .id(2)
                .birthday(LocalDate.of(1984, 12, 21))
                .email("azaza")
                .username("Andrew")
                .login("I_HATE_VALID_EMAILS")
                .build();
        assertFalse(Validator.isValidUser(user));
    }

    @Test
    void shouldReturnFalseIfEmailIsEmpty() {
        User user = User.builder()
                .id(4)
                .birthday(LocalDate.of(1999, 4, 3))
                .email("")
                .username("Eugen Blank")
                .login("E.Blank")
                .build();
        assertFalse(Validator.isValidUser(user));
    }

    @Test
    void shouldReturnFalseIfLoginIsEmpty() {
        User user = User.builder()
                .id(6)
                .birthday(LocalDate.of(1991, 1, 1))
                .email("iam@login.hater")
                .username("Olologin")
                .login("")
                .build();
        assertFalse(Validator.isValidUser(user));
    }

    @Test
    void shouldReturnFalseIfLoginContainsSpaces() {
        User user = User.builder()
                .id(5)
                .birthday(LocalDate.of(1947, 1, 8))
                .email("space@oddity.pun")
                .username("David Bowie")
                .login("Ground control to major Tom")
                .build();
        assertFalse(Validator.isValidUser(user));
    }

    @Test
    void shouldReturnFalseIfUserIsFromTheFuture() {
        User user = User.builder()
                .id(3)
                .birthday(LocalDate.of(2029, 7, 11))
                .email("clothes@boots.motorcycle")
                .username("T800")
                .login("IllBeBack")
                .build();
        assertFalse(Validator.isValidUser(user));
    }

    @Test
    void shouldReturnTrueIfFilmIsValid() {
        Film film = Film.builder()
                .id(1)
                .name("Godfather")
                .description("Stylish guys kill each other")
                .releaseDate(LocalDate.of(1972, 3, 14))
                .duration(175)
                .build();
        assertTrue(Validator.isValidFilm(film));
    }

    @Test
    void shouldReturnFalseIfFilmIsTooOld() {
        Film film = Film.builder()
                .id(2)
                .name("Stoneage")
                .description("Can't beat Lumiere brothers.")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(60)
                .build();
        assertFalse(Validator.isValidFilm(film));
    }

    @Test
    void shouldReturnFalseIfDescriptionLengthIsMoreThan200() {
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
        assertFalse(Validator.isValidFilm(film));
    }

    @Test
    void shouldReturnFalseIfFilmDoesNotContainName() {
        Film film = Film.builder()
                .id(4)
                .name("")
                .description("Hush! I'm anonymous!")
                .releaseDate(LocalDate.of(1999, 12, 27))
                .duration(60)
                .build();
        assertFalse(Validator.isValidFilm(film));
    }

    @Test
    void shouldReturnFalseIfFilmDurationIsNegative() {
        Film film = Film.builder()
                .id(5)
                .name("Pessimist")
                .description("I'm lesser than zero! What a shame!")
                .releaseDate(LocalDate.of(1999, 12, 27))
                .duration(-1)
                .build();
        assertFalse(Validator.isValidFilm(film));
    }

    @Test
    void shouldReturnFalseIfFilmDurationIsZero() {
        Film film = Film.builder()
                .id(6)
                .name("Smashing Pumpkins")
                .description("I'm your lover, i'm your zero")
                .releaseDate(LocalDate.of(1996, 4, 23))
                .duration(0)
                .build();
        assertFalse(Validator.isValidFilm(film));
    }
}
