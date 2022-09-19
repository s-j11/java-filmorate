package ru.yandex.practicum.filmorate.controllers;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exseptions.NotFoundException;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private InMemoryUserStorage inMemoryUserStorage;


    private UserService userService;


    @Autowired
    public UserController(InMemoryUserStorage inMemoryUserStorage, UserService userService) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.userService = userService;
    }

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        return inMemoryUserStorage.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody @NotNull User user) throws ValidationException,NotFoundException {
        return inMemoryUserStorage.addUser(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUsers(@RequestBody @NotNull User user) throws ValidationException, NotFoundException {
        log.error("Ошибка при обновление пользователя");
        return inMemoryUserStorage.updateUser(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByID(@PathVariable Long id) throws NotFoundException {
       return userService.getUserByID(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriends(@PathVariable Long id, @PathVariable Long friendId) throws ValidationException, NotFoundException {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriends(@PathVariable Long id, @PathVariable Long friendId) throws ValidationException, NotFoundException{
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Long> getFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Long> getCommonFriends(@PathVariable long id, @PathVariable long otherId)throws ValidationException,NotFoundException{
        return userService.getCommonFriends(id, otherId);
    }

}