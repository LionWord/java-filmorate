package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class Film {

    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<User> usersThatLiked = new HashSet<>();

    public void addLike(User user) {
        usersThatLiked.add(user);
    }
    public void removeLike(User user) {
        usersThatLiked.remove(user);
    }

    public int getLikesCount() {
        return usersThatLiked.size();
    }
}
