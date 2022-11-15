package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDao {

    User addUser(User user);

    boolean removeUser(User user);

    User updateUser(User user);

    boolean removeUserByEmail(String userEmail);

    User updateUserByEmail(String userEmail);

    User getUserByEmail(String userEmail);

    List<User> getAllUsers(int limit, String direction);

}
