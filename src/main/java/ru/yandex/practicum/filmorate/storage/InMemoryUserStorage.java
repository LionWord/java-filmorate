package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.FailedValidationException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.IdAssigner;
import ru.yandex.practicum.filmorate.utils.Messages;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.*;
@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> database = new HashMap<>();

    @Override
    public void addUser(User user) {
        if (!Validator.isValidUser(user)) {
            throw new FailedValidationException(Messages.FAILED_USER_VALIDATION);
        } else if (database.containsKey(user.getId())) {
            throw new AlreadyExistsException(Messages.USER_ALREADY_EXISTS);
        }

        if (Objects.isNull(user.getName()) || user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.info("Name is empty. Using login instead");
        }

        user.setId(IdAssigner.getUserID());
        IdAssigner.increaseUserID();
        database.put(user.getId(), user);
        log.info("User with ID=" + user.getId() + " successfully added");
    }

    @Override
    public void deleteUser(int userID) {
        if (database.containsKey(userID)) {
            database.remove(userID);
            log.info("User with ID=" + userID + " successfully deleted");
        } else {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
    }

    @Override
    public void modifyUser(User user) {
        if (!Validator.isValidUser(user)) {
            throw new FailedValidationException(Messages.FAILED_USER_VALIDATION);
        } else if (!database.containsKey(user.getId())) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
        database.replace(user.getId(), user);
        log.info("User with ID=" + user.getId() + " successfully modified");
    }

    public List<User> getAllUsers() {
        if (database.isEmpty()) {
            log.info("Database is empty. Returning empty ArrayList");
            return new ArrayList<>();
        } else {
            log.info("Returned values: " + database.values());
            ArrayList<User> result = new ArrayList<>();
            result.addAll(database.values());
            return result;
        }

    }

    public Map<Integer, User> getDatabase() {
        log.info("Returning database");
        return database;
    }

    public boolean userIsPresent(int userID) {
        log.info("Checking if user with ID=" + userID + " is present. Result is " + database.containsKey(userID));
        return database.containsKey(userID);
    }

    public User getUser(int userID) {
        if (!database.containsKey(userID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        } else {
            log.info("Method getUser returned this value - " + database.get(userID));
            return database.get(userID);
        }
    }

}
