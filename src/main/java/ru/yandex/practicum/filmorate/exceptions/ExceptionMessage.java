package ru.yandex.practicum.filmorate.exceptions;

public class ExceptionMessage {

    final String errorCode;
    final String errorMessage;

    public ExceptionMessage(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
