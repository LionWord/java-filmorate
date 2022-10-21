package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FriendsAlreadyException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchFriendException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {

    private final InMemoryUserStorage storage;

    @Autowired
    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    public void addFriend(int userID, int friendID) {
        if (storage.userIsPresent(userID) || storage.userIsPresent(friendID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        } else if (storage.getUser(userID).gotFriend(friendID)) {
            throw new FriendsAlreadyException(Messages.ALREADY_FRIENDS);
        }  else if (storage.getUser(friendID).gotFriend(userID)) {
            throw new FriendsAlreadyException(Messages.ALREADY_FRIENDS);
        }

        storage.getDatabase().get(userID).addFriend(friendID);
        storage.getDatabase().get(friendID).addFriend(userID);

    }

    public void removeFriend(int userID, int friendID) {
        if (storage.userIsPresent(userID) || storage.userIsPresent(friendID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        } else if (!storage.getUser(userID).gotFriend(friendID)) {
            throw new NoSuchFriendException(Messages.NOT_FRIENDS);
        }  else if (!storage.getUser(friendID).gotFriend(userID)) {
            throw new NoSuchFriendException(Messages.NOT_FRIENDS);
        }

        storage.getDatabase().get(userID).removeFriend(friendID);
        storage.getDatabase().get(friendID).removeFriend(userID);

    }

    public List<User> getAllFriendsList(int userID) {
        List<User> friends = new ArrayList<>();
        for (int i : storage.getUser(userID).getFriendsID()) {
            friends.add(storage.getUser(i));
        }
            return friends;
    }

}
