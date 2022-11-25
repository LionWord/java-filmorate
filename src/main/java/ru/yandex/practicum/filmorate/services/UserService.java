package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.implementations.UserDbStorage;

import java.util.*;

@Service
public class UserService implements Friendable {

    private final UserStorage storage;
    private final FriendsStorage friendsStorage;
    @Autowired
    public UserService(UserDbStorage storage, FriendsStorage friendsStorage) {
        this.storage = storage;
        this.friendsStorage = friendsStorage;
    }

    public void addUser(User user) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        storage.addUser(user);
    }
    public void deleteUser(int userID) {
        storage.deleteUser(userID);
    }
    public void modifyUser(User user) {
        storage.modifyUser(user);
    }
    public List<User> getAllUsers() {
        return storage.getAllUsers();
    }
    public boolean userIsPresent(int userID) {
        return storage.userIsPresent(userID);
    }
    public User getUser(int userID) {
        return storage.getUser(userID);
    }

    @Override
    public void addFriend(int userID, int friendID) {
        friendsStorage.addFriends(userID, friendID);
    }
    @Override
    public void removeFriend(int userID, int friendID) {
        friendsStorage.removeFriends(userID, friendID);
    }
    @Override
    public Optional<List<User>> getAllFriendsList(int userID) {
        return friendsStorage.getAllFriends(userID);
    }
    @Override
    public Optional<List<User>> getCommonFriends(int userOneID, int userTwoID) {
        return friendsStorage.getCommonFriends(userOneID, userTwoID);
    }

}
