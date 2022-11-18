package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@Builder(access = AccessLevel.PUBLIC)
public class User {

    private int id;
    private String email;
    private String login;
    private String username;
    private LocalDate birthday;

}
