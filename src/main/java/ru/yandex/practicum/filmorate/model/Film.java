package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.utils.Genres;
import ru.yandex.practicum.filmorate.utils.RatingMPA;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder (access = AccessLevel.PUBLIC)
public class Film {

    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private RatingMPA ratingMPA;
    private int rate;
    private final Set<Genres> genres = new HashSet<>();

}
