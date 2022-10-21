package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FailedValidationException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.Messages;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> database = new HashMap<>();

    @Override
    public void addUser(User user) {
        if (Validator.isValidUser(user)) {
            database.put(user.getId(), user);
        } else {
            throw new FailedValidationException(Messages.FAILED_USER_VALIDATION);
        }
    }

    @Override
    public void deleteUser(int userID) {
        if (database.containsKey(userID)) {
            database.remove(userID);
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
    }

    public Map<Integer, User> getAllUsers() {
        return database;
    }

}
