package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.sql.Date;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder (access = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class Film {

    private int id;
    private String name;
    private String description;
    private Date releaseDate;
    private int duration;
    private Map<String, Integer> mpa;
    private int rate;
    private Map<String, Integer>[] genres;

}
