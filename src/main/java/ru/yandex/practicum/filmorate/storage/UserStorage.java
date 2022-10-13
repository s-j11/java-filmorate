package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.User;

import java.util.List;

public interface UserStorage {

    public List<User> findAllUsers()throws ValidationException, NotFoundException;
    public User addUser(User user)throws ValidationException;

    public void deleteUser(long userID);

    public User updateUser(User user)throws ValidationException;

}
