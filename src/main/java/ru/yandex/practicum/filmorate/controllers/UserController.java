package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.UserService;
import ru.yandex.practicum.filmorate.utils.Messages;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        if (!Validator.isValidUser(user)) {
            throw new FailedValidationException(Messages.FAILED_USER_VALIDATION);
        } else if (service.userIsPresent(user.getId())) {
            throw new AlreadyExistsException(Messages.USER_ALREADY_EXISTS);
        }
        service.addUser(user);
        return user;

    }

    @GetMapping("{id}")
    public User getUser(@PathVariable(value = "id") int userID) {
        if (!service.userIsPresent(userID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
        return service.getUser(userID);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (!Validator.isValidUser(user)) {
            throw new FailedValidationException(Messages.FAILED_USER_VALIDATION);
        } else if (!service.userIsPresent(user.getId())) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
        service.modifyUser(user);
        return user;
    }

    @PutMapping("{id}/friends/{friendId}")
    public void befriendUser(@PathVariable(value = "id") int userID,
                             @PathVariable(value = "friendId") int friendID) {
        if (!service.userIsPresent(userID) || !service.userIsPresent(friendID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        } else if (service.getAllFriendsList(userID).get().contains(service.getUser(friendID))) {
            throw new FriendsAlreadyException(Messages.ALREADY_FRIENDS);
        } else if (service.getAllFriendsList(friendID).get().contains(service.getUser(userID))) {
            throw new FriendsAlreadyException(Messages.ALREADY_FRIENDS);
        }
        service.addFriend(userID, friendID);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void unfriendUser(@PathVariable(value = "id") int userID,
                             @PathVariable(value = "friendId") int friendID) {
        if (!service.userIsPresent(userID) || !service.userIsPresent(friendID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        } else if (!service.getAllFriendsList(userID).get().contains(service.getUser(friendID))) {
            throw new NoSuchFriendException(Messages.NOT_FRIENDS);
        } else if (!service.getAllFriendsList(friendID).get().contains(service.getUser(userID))) {
            throw new NoSuchFriendException(Messages.NOT_FRIENDS);
        }
        service.removeFriend(userID, friendID);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Optional<List<User>> getCommonFriends(@PathVariable(value = "id") int userOneID,
                                           @PathVariable(value = "otherId") int userTwoID) {
        if (service.userIsPresent(userOneID) || service.userIsPresent(userTwoID)) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
        return service.getCommonFriends(userOneID, userTwoID);
    }

    @GetMapping("{id}/friends")
    public Optional<List<User>> getUserFriends(@PathVariable(value = "id") int userID) {
        return service.getAllFriendsList(userID);
    }
}
