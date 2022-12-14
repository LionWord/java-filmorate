package ru.yandex.practicum.filmorate.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {

    public static final String FAILED_USER_VALIDATION = "Failed user validation. Check your input and try again.\n" +
            "Input considered invalid if any of following is present:\n"
            + "1) E-mail is empty\n"
            + "2) '@' symbol is absent\n"
            + "3) Birthdate is in the future\n"
            + "4) Login contains 'space' symbol\n"
            + "5) Login is empty\n";

    public static final String FAILED_FILM_VALIDATION = "Failed film validation. Check your input and try again.\n" +
            "Input considered invalid if any of following is present:\n"
            + "1) Name is empty\n"
            + "2) Description length is more than 200\n"
            + "3) Duration is less than or equal 0\n"
            + "4) Release date is before 28.12.1895\n";

    public static final String NO_SUCH_USER = "User with this ID does not exist";
    public static final String NO_SUCH_FILM = "Film with this ID does not exist";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String FILM_ALREADY_EXISTS = "Film already exists";
    public static final String ALREADY_FRIENDS = "Users are already friends";
    public static final String NOT_FRIENDS = "Users are not friends";
    public static final String TOO_BIG_VALUE = "Value is too big";
    public static final String INVALID_INPUT = "Invalid input";
    public static final String INVALID_MPA = "Invalid MPA ID";
    public static final String INVALID_GENRE = "Invalid genre ID";
    public static final String TRY_ANOTHER_ID = "Try another filmID or userID";

}
