package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.utils.Messages;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({InvalidInputException.class, FailedValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleRequestExceptions(RuntimeException e) {
        return new ExceptionMessage(Messages.REQUEST_ERROR, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage handleNoSuchEntryException(NoSuchEntryException e) {
        return new ExceptionMessage(Messages.REQUEST_ERROR, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionMessage handleAlreadyExistsException(AlreadyExistsException e) {
        return new ExceptionMessage(Messages.REQUEST_ERROR, e.getMessage());
    }

}
