package ru.yandex.practicum.filmorate.storage.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.DAO.FriendshipDao;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class FriendsDbStorage implements FriendsStorage {

    private final FriendshipDao friendshipDao;

    @Autowired
    public FriendsDbStorage(FriendshipDao friendshipDao) {
        this.friendshipDao = friendshipDao;
    }

    @Override
    public void addFriendshipRequest(int senderID, int recipientID) {
        friendshipDao.sendFriendshipRequest(senderID,recipientID);
    }

    @Override
    public Map<Integer, Integer> addFriends(int acceptorID, int requesterID) {
        return friendshipDao.acceptFriendshipRequest(acceptorID, requesterID);
    }

    @Override
    public boolean removeFriends(int userID, int friendID) {
        return friendshipDao.removeFriends(userID, friendID);
    }

    @Override
    public Optional<List<User>> getAllFriends(int userID) {
        return friendshipDao.getAllUserFriends(userID);
    }

    @Override
    public Optional<List<User>> getCommonFriends(int firstUserID, int secondUserID) {
        return friendshipDao.getCommonFriends(firstUserID, secondUserID);
    }


}
