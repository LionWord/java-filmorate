package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.InvalidInputException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.IdAssigner;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final HashMap<Integer, User> database = new HashMap<>();

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(database.values());
    }

    @PostMapping
    public User addUser(@RequestBody User user) throws InvalidInputException, AlreadyExistsException {
        if (!Validator.isValidUser(user)) {
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
        if (!Validator.isValidUser(user)) {
            throw new InvalidInputException("Invalid input.");
        }
        if (!database.containsKey(user.getId())) {
            throw new InvalidInputException("Invalid input.");
        }
        database.put(user.getId(), user);
        log.info("User entry" + user.getName() + " succesfully updated.");
        return user;
    }

    @PutMapping("{id}/friends/{friendId}")
    public Map<String, String> befriendUser(@PathVariable(value = "id") int userID,
                                            @PathVariable(value = "friendId") int friendID) {
        return
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public Map<String, String> unfriendUser(@PathVariable(value = "id") int userID,
                                            @PathVariable(value = "friendId") int friendID) {
        return
    }

}
