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
public class Film {

    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private final Set<User> usersThatLiked = new HashSet<>();

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
