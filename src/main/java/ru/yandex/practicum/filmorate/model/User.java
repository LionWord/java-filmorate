package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@Builder
public class User {

    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

}
