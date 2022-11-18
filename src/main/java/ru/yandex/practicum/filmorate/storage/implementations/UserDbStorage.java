package ru.yandex.practicum.filmorate.storage.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.UserDao;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class UserDbStorage implements UserStorage {

    UserDao userDao;

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
    public void modifyUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public boolean userIsPresent(int userID) {
        return !isEmpty(userDao.getUserById(userID));
    }

    @Override
    public User getUser(int userID) {
        userDao.getUserById(userID);
    }
}
