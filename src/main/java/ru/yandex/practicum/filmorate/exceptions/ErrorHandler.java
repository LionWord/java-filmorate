package ru.yandex.practicum.filmorate.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handleInvalidInputException(InvalidInputException e) {
        log.error(e.getMessage());
        return new ExceptionMessage("500", "Internal server error");
    }

    @ExceptionHandler(FailedValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleFailedValidationException(FailedValidationException e) {
        log.error(e.getMessage());
        return new ExceptionMessage("400", "Failed validation");
    }

    @ExceptionHandler(NoSuchEntryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage handleNoSuchEntryException(NoSuchEntryException e) {
        log.error(e.getMessage());
        return new ExceptionMessage("404", "Not found");
    }

    @ExceptionHandler({AlreadyExistsException.class, FriendsAlreadyException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handleConflictException(RuntimeException e) {
        log.error(e.getMessage());
        return new ExceptionMessage("500", "Internal server error");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handleUnknownException(RuntimeException e) {
        log.error(e.getMessage());
        return new ExceptionMessage("500", "Internal server error");
    }

}
