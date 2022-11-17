package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User addUser(User user);

    void removeUser(User user);

    User updateUser(User user);

    boolean removeUserByEmail(String userEmail);

    Optional<User> getUserByEmail(String userEmail);

    List<User> getAllUsers(int limit, String sortBy, String direction);

}
