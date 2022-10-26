package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.Friendable;
import ru.yandex.practicum.filmorate.services.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.utils.Messages;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserStorage storage;
    private final Friendable service;

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
        if (!Validator.isValidUser(user)) {
            throw new FailedValidationException(Messages.FAILED_USER_VALIDATION);
        } else if (storage.getDatabase().containsKey(user.getId())) {
            throw new AlreadyExistsException(Messages.USER_ALREADY_EXISTS);
        }
        storage.addUser(user);
        return user;

    }

    @GetMapping("{id}")
    public User getUser(@PathVariable(value = "id") int userID) {
        if (!storage.getDatabase().containsKey(userID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
        return storage.getUser(userID);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (!Validator.isValidUser(user)) {
            throw new FailedValidationException(Messages.FAILED_USER_VALIDATION);
        } else if (!storage.getDatabase().containsKey(user.getId())) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
        storage.modifyUser(user);
        return user;
    }

    @PutMapping("{id}/friends/{friendId}")
    public void befriendUser(@PathVariable(value = "id") int userID,
                             @PathVariable(value = "friendId") int friendID) {
        if (storage.userIsPresent(userID) || storage.userIsPresent(friendID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        } else if (storage.getUser(userID).gotFriend(friendID)) {
            throw new FriendsAlreadyException(Messages.ALREADY_FRIENDS);
        } else if (storage.getUser(friendID).gotFriend(userID)) {
            throw new FriendsAlreadyException(Messages.ALREADY_FRIENDS);
        }
        service.addFriend(userID, friendID);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void unfriendUser(@PathVariable(value = "id") int userID,
                             @PathVariable(value = "friendId") int friendID) {
        if (storage.userIsPresent(userID) || storage.userIsPresent(friendID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        } else if (!storage.getUser(userID).gotFriend(friendID)) {
            throw new NoSuchFriendException(Messages.NOT_FRIENDS);
        } else if (!storage.getUser(friendID).gotFriend(userID)) {
            throw new NoSuchFriendException(Messages.NOT_FRIENDS);
        }
        service.removeFriend(userID, friendID);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable(value = "id") int userOneID,
                                      @PathVariable(value = "otherId") int userTwoID) {
        if (storage.userIsPresent(userOneID) || storage.userIsPresent(userTwoID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
        return service.getCommonFriends(userOneID, userTwoID);
    }

    @GetMapping("{id}/friends")
    public List<User> getUserFriends(@PathVariable(value = "id") int userID) {
        return service.getAllFriendsList(userID);
    }
}
