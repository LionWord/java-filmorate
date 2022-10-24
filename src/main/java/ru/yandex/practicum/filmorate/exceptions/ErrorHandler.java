package ru.yandex.practicum.filmorate.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.utils.Messages;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({InvalidInputException.class, FailedValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleRequestExceptions(RuntimeException e) {
        log.error(e.getMessage());
        return new ExceptionMessage(Messages.REQUEST_ERROR, e.getMessage());
    }

    @ExceptionHandler(NoSuchEntryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage handleNoSuchEntryException(NoSuchEntryException e) {
        log.error(e.getMessage());
        return new ExceptionMessage(Messages.REQUEST_ERROR, e.getMessage());
    }

    @ExceptionHandler ({AlreadyExistsException.class, FriendsAlreadyException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionMessage handleConflictException(RuntimeException e) {
        log.error(e.getMessage());
        return new ExceptionMessage(Messages.REQUEST_ERROR, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public ExceptionMessage handleNoCommonFriendsException(NoCommonFriendsException e) {
        log.info(e.getMessage());
        return new ExceptionMessage(Messages.NOTHING_TO_RETURN, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handleUnknownException(RuntimeException e) {
        log.error(Messages.UNKNOWN_ERROR);
        return new ExceptionMessage(Messages.SERVER_ERROR, Messages.UNKNOWN_ERROR);
    }

}
