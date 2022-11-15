package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface FriendshipDao {

    boolean sendFriendshipRequest(int senderID, int recipientID);

    Map<User, User> acceptFriendshipRequest(int acceptorID, int requesterID);

}
