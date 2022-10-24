package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FriendsAlreadyException;
import ru.yandex.practicum.filmorate.exceptions.NoCommonFriendsException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchFriendException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final UserStorage storage;

    @Autowired
    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    public void addFriend(int userID, int friendID) {
        if (!storage.userIsPresent(userID) || !storage.userIsPresent(friendID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        } else if (storage.getUser(userID).gotFriend(friendID)) {
            throw new FriendsAlreadyException(Messages.ALREADY_FRIENDS);
        }  else if (storage.getUser(friendID).gotFriend(userID)) {
            throw new FriendsAlreadyException(Messages.ALREADY_FRIENDS);
        }

        storage.getDatabase().get(userID).addFriend(friendID);
        storage.getDatabase().get(friendID).addFriend(userID);
        log.info(storage.getUser(userID) + " and " + storage.getUser(friendID) + " now friends");

    }

    public void removeFriend(int userID, int friendID) {
        if (!storage.userIsPresent(userID) || !storage.userIsPresent(friendID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        } else if (!storage.getUser(userID).gotFriend(friendID)) {
            throw new NoSuchFriendException(Messages.NOT_FRIENDS);
        }  else if (!storage.getUser(friendID).gotFriend(userID)) {
            throw new NoSuchFriendException(Messages.NOT_FRIENDS);
        }

        storage.getDatabase().get(userID).removeFriend(friendID);
        storage.getDatabase().get(friendID).removeFriend(userID);
        log.info(storage.getUser(userID) + " and " + storage.getUser(friendID) + "are not friends");
    }

    public List<User> getAllFriendsList(int userID) {
        if (storage.getUser(userID).getFriendsID().isEmpty()) {
            log.info("User does not have friends. Returning empty ArrayList");
            return new ArrayList<>();
        }
        List<User> friends = new ArrayList<>();
        for (int i : storage.getUser(userID).getFriendsID()) {
            friends.add(storage.getUser(i));
        }
        log.info("Method getAllFriendsList is finished");
            return friends;
    }

    public Set<User> getCommonFriends(int userOneID, int userTwoID) {
        if (!storage.userIsPresent(userOneID) || !storage.userIsPresent(userTwoID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
        Set<Integer> firstUserFriends = storage.getUser(userOneID).getFriendsID();
        Set<Integer> secondUserFriends = storage.getUser(userTwoID).getFriendsID();
        if (firstUserFriends.isEmpty() || secondUserFriends.isEmpty()) {
            //throw new NoCommonFriendsException(Messages.NO_COMMON_FRIENDS);
            return new HashSet<>();
        }
        Set<User> result = new HashSet<>();
        if (firstUserFriends.retainAll(secondUserFriends)) {
            for (int i : firstUserFriends) {
                result.add(storage.getUser(i));
            }
            log.info("Method getCommonFriends is finished");
            return result;
        } else {
            throw new NoCommonFriendsException(Messages.NO_COMMON_FRIENDS);
        }
    }

}
