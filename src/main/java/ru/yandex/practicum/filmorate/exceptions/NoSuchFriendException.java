package ru.yandex.practicum.filmorate.exceptions;

public class NoSuchFriendException extends RuntimeException {
    public NoSuchFriendException(String message) {
        super(message);
    }
}
