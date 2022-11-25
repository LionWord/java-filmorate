package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.io.Serializable;
import java.util.Comparator;

@Getter
@Setter
@ToString
@Builder(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class Genre implements Serializable {
    private int id;
    private String name;

    public static Comparator<Genre> getComparator() {
        return (g1, g2) -> g1.getId() - g2.getId();
    }

}
