package ru.yandex.practicum.filmorate.controllers;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private int id = 1;
    private Map<Integer, User> users = new HashMap<>();

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        List<User> inventoryUsers = new ArrayList<>();
        for (Map.Entry<Integer, User> entry : users.entrySet()){
            inventoryUsers.add(entry.getValue());
        }
        return inventoryUsers;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody @NonNull User user) {
        user.setId(id);
        id++;
        log.error("Ошибка при добовление пользователя");
            try {
                if (users.containsKey(user.getId())) {
                    throw new ValidationException("Пользователь уже существует");
                }else if(user.getEmail().isEmpty()){
                    throw new ValidationException("E-mail не введен");
                }else if(user.getEmail().contains("'@'")){
                    throw new ValidationException("E-mail должен содержать символ @");
                }else if(user.getLogin().isEmpty()){
                    throw new ValidationException("Логин не введен");
                }else if(user.getLogin().contains(" ")){
                    throw new ValidationException("Логин не должен содержать пробелы");
                }else if(user.getBirthday().isAfter(LocalDate.now())){
                    throw new ValidationException("Дата рождение не может быть в будущем");
                } else {
                    if(user.getName().isEmpty()){
                        user.setName(user.getLogin());
                        users.put(user.getId(),user);
                    }else {
                        users.put(user.getId(),user);
                    }
                }
            }catch (ValidationException e){
                e.getMessage();
            }
        return user;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUsers(@RequestBody @NonNull User user) {
        log.error("Ошибка при обновление пользователя");
            try {
                if (!users.containsKey(user.getId())) {
                throw new ValidationException("Нет такого пользователя");
                }else if (user.getEmail().isEmpty()) {
                    throw new ValidationException("E-mail не введен");
                } else if (user.getEmail().contains("'@'")) {
                    throw new ValidationException("E-mail должен содержать символ @");
                } else if (user.getLogin().isEmpty()) {
                    throw new ValidationException("Логин не введен");
                } else if (user.getLogin().contains(" ")) {
                    throw new ValidationException("Логин не должен содержать пробелы");
                } else if (user.getBirthday().isAfter(LocalDate.now())) {
                    throw new ValidationException("Дата рождение не может быть в будущем");
                } else {
                    if(user.getName().isEmpty()){
                        user.setName(user.getLogin());
                        users.put(user.getId(),user);
                    }else {
                        users.put(user.getId(),user);
                    }
                }
            } catch (ValidationException e) {
                e.getMessage();

        }
            return user;
    }
}