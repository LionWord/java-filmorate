package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class Genre {
    private int id;
    private String name;
}
