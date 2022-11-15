package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.IdAssigner;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.util.*;

@Slf4j
@Repository
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> database = new HashMap<>();

    @Override
    public void addUser(User user) {
        if (Objects.isNull(user.getUsername()) || user.getUsername().isEmpty()) {
            user.setUsername(user.getLogin());
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
        database.replace(user.getId(), user);
        log.info("User with ID=" + user.getId() + " successfully modified");
    }

    public List<User> getAllUsers() {
        if (database.isEmpty()) {
            log.info("Database is empty. Returning empty ArrayList");
            return new ArrayList<>();
        } else {
            log.info("Returned values: " + database.values());
            return new ArrayList<>(database.values());
        }

    }

    public Map<Integer, User> getDatabase() {
        log.info("Returning database");
        return database;
    }

    public boolean userIsPresent(int userID) {
        log.info("Checking if user with ID=" + userID + " is present. Result is " + database.containsKey(userID));
        return !database.containsKey(userID);
    }

    public User getUser(int userID) {
            log.info("Method getUser returned this value - " + database.get(userID));
            return database.get(userID);
    }

}
