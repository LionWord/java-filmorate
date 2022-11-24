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
        return new Comparator<Genre>() {
            @Override
            public int compare(Genre g1, Genre g2) {
                return g1.getId() - g2.getId();
            }
        };
    }

}
