package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@Builder(access = AccessLevel.PUBLIC)
@EqualsAndHashCode

public class User implements Serializable {

    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

}
