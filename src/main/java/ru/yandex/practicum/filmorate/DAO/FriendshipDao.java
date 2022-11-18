package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FriendshipDao {

    boolean sendFriendshipRequest(int senderID, int recipientID);

    Map<Integer, Integer> acceptFriendshipRequest(int acceptorID, int requesterID);

    Optional<List<User>> getAllUserFriends(int userID);

    Optional<List<User>> getCommonFriends(int firstUserID, int secondUserID);

    boolean removeFriends(int userID, int friendID);
}
