package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FriendshipDao {

    boolean sendFriendshipRequest(String senderEmail, String recipientEmail);

    Map<String, String> acceptFriendshipRequest(String acceptorEmail, String requesterEmail);

    Optional<List<User>> getAllUserFriends(String userEmail);

    Optional<List<User>> getCommonFriends(String firstUserEmail, String secondUserEmail);

}
