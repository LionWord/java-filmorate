package ru.yandex.practicum.filmorate.storage.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDbStorage implements UserStorage {

    private final UserDao userDao;

    @Autowired
    public UserDbStorage(UserDao userDaoImpl) {
        this.userDao = userDaoImpl;
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void deleteUser(int userID) {
        userDao.removeUserById(userID);
    }

    @Override
    public User modifyUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public boolean userIsPresent(int userID) {
        return Optional.ofNullable(userDao.getUserById(userID)).isPresent();
    }

    @Override
    public User getUser(int userID) {
        return userDao.getUserById(userID);
    }
}
