package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.InvalidInputException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.IdAssigner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final HashMap<Integer, User> database = new HashMap<>();

    public static boolean isInvalidUserInput(User user) {
        return user.getEmail().isEmpty()
                || !user.getEmail().contains("@")
                || user.getBirthday().isAfter(LocalDate.now())
                || user.getLogin().contains(" ")
                || user.getLogin().isEmpty()
                || user.getLogin() == null;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(database.values());
    }

    @PostMapping
    public User addUser(@RequestBody User user) throws InvalidInputException, AlreadyExistsException {
        if (isInvalidUserInput(user)) {
            throw new InvalidInputException("Invalid input.");
        }

        if (database.containsKey(user.getId())) {
            throw new AlreadyExistsException("User already exists.");
        }

        if (Objects.isNull(user.getName())) {
            user.setName(user.getLogin());
        }

        user.setId(IdAssigner.getUserID());
        IdAssigner.increaseUserID();
        database.put(user.getId(), user);
        log.info("User entry " + user.getName() + " succesfully added.");
        return user;

    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws InvalidInputException {
        if (isInvalidUserInput(user)) {
            throw new InvalidInputException("Invalid input.");
        }
        if (!database.containsKey(user.getId())) {
            throw new InvalidInputException("Invalid input.");
        }
        database.put(user.getId(), user);
        log.info("User entry" + user.getName() + " succesfully updated.");
        return user;
    }
}
