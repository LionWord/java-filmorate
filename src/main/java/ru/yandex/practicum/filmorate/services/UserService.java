package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.implementations.UserDbStorage;

import java.util.*;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Slf4j
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
        log.debug("Starting method addFriend");
        friendsStorage.addFriends(userID, friendID);
        log.debug("Finished method addFriend");
    }
    @Override
    public void removeFriend(int userID, int friendID) {
        log.debug("Starting method removeFriend");
        friendsStorage.removeFriends(userID, friendID);
        log.info(userID + " and " + friendID + "are not friends");
        log.debug("Finished method removeFriend");
    }
    @Override
    public Optional<List<User>> getAllFriendsList(int userID) {
        log.debug("Starting method getAllFriendsList");
        return friendsStorage.getAllFriends(userID);
    }
    @Override
    public Optional<List<User>> getCommonFriends(int userOneID, int userTwoID) {
        log.debug("Starting method getCommonFriends");
        return friendsStorage.getCommonFriends(userOneID, userTwoID);
    }

}
