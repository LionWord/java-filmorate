package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    User addUser(User user);

    User updateUser(User user);

    boolean removeUserById(int userID);

    User getUserById(int userID);

    List<User> getAllUsers();

    User makeUser(ResultSet rs) throws SQLException;

}
