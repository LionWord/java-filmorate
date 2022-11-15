package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@Builder
public class User {

    private int id;
    private String email;
    private String login;
    private String username;
    private LocalDate birthday;
    private final Set<Integer> friendsID = new HashSet<>();

    public void addFriend(int friendID) {
        friendsID.add(friendID);
    }

    public void removeFriend(int friendID) {
        friendsID.remove(friendID);
    }

    public boolean gotFriend(int friendID) {
        return friendsID.contains(friendID);
    }
}
