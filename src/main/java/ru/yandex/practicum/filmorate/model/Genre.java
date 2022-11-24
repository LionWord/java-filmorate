package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class Genre implements Serializable {
    private int id;
    private String name;
}
