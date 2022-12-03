package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    void addUser(User user);

    void deleteUser(int userID);

    User modifyUser(User user);

    List<User> getAllUsers();

    boolean userIsPresent(int userID);

    User getUser(int userID);


}
