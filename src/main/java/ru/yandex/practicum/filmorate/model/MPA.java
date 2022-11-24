package ru.yandex.practicum.filmorate.model;


import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder(access = AccessLevel.PUBLIC)
@EqualsAndHashCode

public class MPA implements Serializable {
    private int id;
    private String name;

}
