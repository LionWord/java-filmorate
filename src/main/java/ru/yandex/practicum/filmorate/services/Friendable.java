package ru.yandex.practicum.filmorate.services;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Friendable {
    void addFriend(int userID, int friendID);
    void removeFriend(int userID, int friendID);
    Optional<List<User>> getAllFriendsList(int userID);
    Optional<List<User>> getCommonFriends(int userOneID, int userTwoID);

}
