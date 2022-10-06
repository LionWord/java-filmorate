package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.InvalidInputException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private HashMap<Integer, User> database = new HashMap<>();
    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(database.values());
    }
    @PostMapping
    public User addUser(@RequestBody User user) throws InvalidInputException, AlreadyExistsException {
        if (isInvalidUserInput(user)) {
            log.debug("Invalid input.");
            throw new InvalidInputException("Invalid input.");
        }
        if (database.containsKey(user.getId())) {
            log.debug("User already exists.");
            throw new AlreadyExistsException("User already exists.");
        } else {
            database.put(user.getId(), user);
            log.info("User entry " + user.getName() + " succesfully added.");
            return user;
        }
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws InvalidInputException {
        if (isInvalidUserInput(user)) {
            log.debug("Invalid input.");
            throw new InvalidInputException("Invalid input.");
        }
        database.put(user.getId(), user);
        log.info("User entry" + user.getName() + " succesfully updated.");
        return user;
    }

    public boolean isInvalidUserInput(User user) {
        return user.getEmail().isEmpty()
                || !user.getEmail().contains("@")
                || user.getBirthday().isAfter(LocalDate.now())
                || !user.getLogin().contains(" ")
                || user.getLogin().isEmpty();
    }
}
