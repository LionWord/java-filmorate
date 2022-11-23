package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import ru.yandex.practicum.filmorate.utils.Genres;
import ru.yandex.practicum.filmorate.utils.RatingMPA;

import java.time.LocalDate;
import java.sql.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

}
