package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Builder(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class Film implements Serializable {

    private int id;
    private String name;
    private String description;
    private Date releaseDate;
    private int duration;
    private MPA mpa;
    private int rate;
    @Nullable
    private List<Genre> genres;

}
