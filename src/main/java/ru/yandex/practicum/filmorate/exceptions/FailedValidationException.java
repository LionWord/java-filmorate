package ru.yandex.practicum.filmorate.exceptions;

public class FailedValidationException extends RuntimeException {
    public FailedValidationException(String message) {
        super(message);
    }
}
