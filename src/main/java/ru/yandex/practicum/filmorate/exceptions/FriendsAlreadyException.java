package ru.yandex.practicum.filmorate.exceptions;

public class FriendsAlreadyException extends RuntimeException {
    public FriendsAlreadyException(String message) {
        super(message);
    }
}
