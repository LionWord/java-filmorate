package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    User addUser(User user);

    User updateUser(User user);

    boolean removeUserById(int userID);

    Optional<User> getUserById(int userID);

    List<User> getAllUsers();

    User makeUser(ResultSet rs) throws SQLException;

}
