package ru.yandex.practicum.filmorate.exceptions;

public class ExceptionMessage {

    final String errorLocation;
    final String errorMessage;

    public ExceptionMessage(String errorLocation, String errorMessage) {
        this.errorLocation = errorLocation;
        this.errorMessage = errorMessage;
    }

    public String getErrorLocation() {
        return errorLocation;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
