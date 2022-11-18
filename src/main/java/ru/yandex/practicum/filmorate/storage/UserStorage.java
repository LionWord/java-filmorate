package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {

    void addUser(User user);

    void deleteUser(int userID);

    void modifyUser(User user);

    List<User> getAllUsers();

    boolean userIsPresent(int userID);

    User getUser(int userID);


}
