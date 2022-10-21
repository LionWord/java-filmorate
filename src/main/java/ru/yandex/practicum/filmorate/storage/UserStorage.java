package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

    void addUser(User user);

    void deleteUser(int userID);

    void modifyUser(User user);

}
