package ru.yandex.practicum.filmorate.services;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;

public interface Friendable {
    void addFriend(int userID, int friendID);
    void removeFriend(int userID, int friendID);
    List<User> getAllFriendsList(int userID);
    Set<User> getCommonFriends(int userOneID, int userTwoID);

}
