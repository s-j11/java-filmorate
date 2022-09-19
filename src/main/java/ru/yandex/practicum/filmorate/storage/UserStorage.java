package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exseptions.NotFoundException;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;

import java.util.List;

public interface UserStorage {


    public List<User> findAll()throws ValidationException, NotFoundException;
    public User addUser(User user)throws ValidationException;

    public void deleteUser(long userID);

    public User updateUser(User user)throws ValidationException;

}
