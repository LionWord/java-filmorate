package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;


@Getter
@ToString
@Builder
public class User {

    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Integer> friendsID;

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

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
