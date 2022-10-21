package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FriendsAlreadyException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.util.Map;

@Component
public class UserService {

    private final InMemoryUserStorage storage;

    @Autowired
    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    public void addFriend(int userID, int friendID) {
        Map<Integer, User> database = storage.getDatabase();
        if (database.containsKey(userID) || database.containsKey(friendID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        } else if (database.get(userID).getFriendsID().contains(friendID)) {
            throw new FriendsAlreadyException(Messages.ALREADY_FRIENDS);
        } else if (database.get(friendID).getFriendsID().contains(userID)) {
            throw new FriendsAlreadyException(Messages.ALREADY_FRIENDS);
        }

        database.get(userID).addFriend(friendID);
        database.get(friendID).addFriend(userID);


    }

}
