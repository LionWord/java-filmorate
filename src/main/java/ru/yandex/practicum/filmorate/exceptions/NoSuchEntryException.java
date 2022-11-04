package ru.yandex.practicum.filmorate.exceptions;

public class NoSuchEntryException extends RuntimeException {
    public NoSuchEntryException(String message) {
        super(message);
    }
}
