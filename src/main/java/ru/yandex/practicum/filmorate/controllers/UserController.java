package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.FailedValidationException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.utils.IdAssigner;
import ru.yandex.practicum.filmorate.utils.Messages;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    InMemoryUserStorage storage;
    @Autowired
    public UserController(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return storage.getAllUsers();

    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        storage.addUser(user);
        return user;

    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        storage.modifyUser(user);
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
