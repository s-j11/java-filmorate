package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody @NotNull User user) throws ValidationException,NotFoundException {
        return userService.addUser(user);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUsers(@RequestBody @NotNull User user) throws ValidationException, NotFoundException {
        log.error("Ошибка при обновление пользователя");
        return userService.updateUser(user);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByID(@PathVariable Long id) throws NotFoundException {
       return userService.getUserByID(id);
    }
    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriends(@PathVariable Long id, @PathVariable Long friendId) throws ValidationException,
            NotFoundException {
        userService.addFriend(id, friendId);
    }
    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriends(@PathVariable Long id, @PathVariable Long friendId) throws ValidationException,
            NotFoundException{
        userService.deleteFriend(id, friendId);
    }
    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }
    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId)throws NotFoundException{
        return userService.getCommonFriends(id, otherId);
    }

}