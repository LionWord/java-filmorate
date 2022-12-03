package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FriendsStorage {

    void addFriendshipRequest(int senderID, int recipientID);

    Map<Integer, Integer> addFriends(int acceptorID, int requesterID);

    boolean removeFriends(int userID, int friendID);

    Optional<List<User>> getAllFriends(int userID);

    Optional<List<User>> getCommonFriends(int firstUserID, int secondUserID);

}
