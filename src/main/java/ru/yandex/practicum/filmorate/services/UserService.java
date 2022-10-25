package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService implements Friendable {

    private final UserStorage storage;

    @Autowired
    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }
    @Override
    public void addFriend(int userID, int friendID) {
        log.debug("Starting method addFriend");
        User userOne = storage.getUser(userID);
        userOne.addFriend(friendID);
        storage.modifyUser(userOne);
        User userTwo = storage.getUser(friendID);
        userTwo.addFriend(userID);
        storage.modifyUser(userTwo);
        log.info(storage.getUser(userID) + " and " + storage.getUser(friendID) + " now friends");
        log.debug("Finished method addFriend");
    }
    @Override
    public void removeFriend(int userID, int friendID) {
        log.debug("Starting method removeFriend");
        storage.getDatabase().get(userID).removeFriend(friendID);
        storage.getDatabase().get(friendID).removeFriend(userID);
        log.info(storage.getUser(userID) + " and " + storage.getUser(friendID) + "are not friends");
        log.debug("Finished method removeFriend");
    }
    @Override
    public List<User> getAllFriendsList(int userID) {
        log.debug("Starting method getAllFriendsList");
        if (storage.getUser(userID).getAcceptedFriendsID().isEmpty()) {
            log.info("User does not have friends. Returning empty ArrayList");
            return new ArrayList<>();
        }
        List<User> friends = new ArrayList<>();
        for (int i : storage.getUser(userID).getAcceptedFriendsID()) {
            friends.add(storage.getUser(i));
        }
        log.info("Finished method getAllFriendsList");
        return friends;
    }
    @Override
    public Set<User> getCommonFriends(int userOneID, int userTwoID) {
        log.debug("Starting method getCommonFriends");
        Set<Integer> firstUserFriends = storage.getUser(userOneID).getAcceptedFriendsID();
        Set<Integer> secondUserFriends = storage.getUser(userTwoID).getAcceptedFriendsID();
        if (firstUserFriends.isEmpty() || secondUserFriends.isEmpty()) {
            return new HashSet<>();
        }
        Set<User> result = new HashSet<>();
        for (int i : firstUserFriends) {
            if (secondUserFriends.contains(i)) {
                result.add(storage.getUser(i));
            }
        }
        return result;
    }

}
