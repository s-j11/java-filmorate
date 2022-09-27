package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exseptions.NotFoundException;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private  long id = 1;
    private Map<Long, User> users = new HashMap<>();

    public Map<Long, User> getUsers() {
        return users;
    }

    @Override
    public List<User> findAll() {
            List<User> inventoryUsers = new ArrayList<>();
            for (Map.Entry<Long, User> entry : users.entrySet()){
                inventoryUsers.add(entry.getValue());
            }
            return inventoryUsers;
    }

    @Override
    public User addUser(User user) throws ValidationException, NotFoundException {

        if (users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь уже существует");
        } else {
            if (user.getEmail().isEmpty()) {
                throw new ValidationException("E-mail не введен");
            }
            if (user.getEmail().contains("'@'")) {
                throw new ValidationException("E-mail должен содержать символ @");
            }
            if (user.getLogin().isEmpty()) {
                throw new ValidationException("Логин не введен");
            }
            if (user.getLogin().contains(" ")) {
                throw new ValidationException("Логин не должен содержать пробелы");
            }
            if (user.getBirthday().isAfter(LocalDate.now())) {
                throw new ValidationException("Дата рождение не может быть в будущем");
            }
            if (user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            user.setId(id);
            users.put(user.getId(), user);
            id++;
        }
        return user;
    }

    @Override
    public void deleteUser(long userID) throws NotFoundException {
        if (users.containsKey(userID)){
            throw new NotFoundException("Таково пользователя нет");
        }else {
            users.remove(userID);
        }
    }

    @Override
    public User updateUser(User user) throws NotFoundException, ValidationException{
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Нет такого пользователя");
        } else {
            if (user.getEmail().isEmpty()) {
                throw new ValidationException("E-mail не введен");
            }
            if (user.getEmail().contains("'@'")) {
                throw new ValidationException("E-mail должен содержать символ @");
            }
            if (user.getLogin().isEmpty()) {
                throw new ValidationException("Логин не введен");
            }
            if (user.getLogin().contains(" ")) {
                throw new ValidationException("Логин не должен содержать пробелы");
            }
            if (user.getBirthday().isAfter(LocalDate.now())) {
                throw new ValidationException("Дата рождение не может быть в будущем");
            }
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
            return user;
        }
    }
}
