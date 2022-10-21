package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.FailedValidationException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.IdAssigner;
import ru.yandex.practicum.filmorate.utils.Messages;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.*;
import java.util.stream.Stream;

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
        }

        user.setId(IdAssigner.getUserID());
        IdAssigner.increaseUserID();
        database.put(user.getId(), user);

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

    public List<User> getAllUsers() {
        return (List<User>) database.values();
    }

    public Map<Integer, User> getDatabase() {
        return database;
    }

}
