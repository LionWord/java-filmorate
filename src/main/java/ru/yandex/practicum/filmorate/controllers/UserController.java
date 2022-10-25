package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserStorage storage;
    private final UserService service;

    @Autowired
    public UserController(InMemoryUserStorage storage, UserService service) {
        this.storage = storage;
        this.service = service;
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

    @GetMapping("{id}")
    public User getUser(@PathVariable(value = "id") int userID) {
        return storage.getUser(userID);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        storage.modifyUser(user);
        return user;
    }

    @PutMapping("{id}/friends/{friendId}")
    public void befriendUser(@PathVariable(value = "id") int userID,
                             @PathVariable(value = "friendId") int friendID) {
        service.addFriend(userID, friendID);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void unfriendUser(@PathVariable(value = "id") int userID,
                             @PathVariable(value = "friendId") int friendID) {
        service.removeFriend(userID, friendID);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable(value = "id") int userOneID,
                                      @PathVariable(value = "otherId") int userTwoID) {
        return service.getCommonFriends(userOneID, userTwoID);
    }

    @GetMapping("{id}/friends")
    public List<User> getUserFriends(@PathVariable(value = "id") int userID) {
        return service.getAllFriendsList(userID);
    }
}
