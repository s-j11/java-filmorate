package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exseptions.NotFoundException;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.*;
@Service
public class UserService {
    private InMemoryUserStorage inMemoryUserStorage;
    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }
    public InMemoryUserStorage getInMemoryUserStorage() {
        return inMemoryUserStorage;
    }

    private Map<Long, User> users = new HashMap<>();
    public List<User> findAllUsers(){
        return inMemoryUserStorage.findAllUsers();
    }

    public User addUser(User user) throws ValidationException, NotFoundException {
        users = inMemoryUserStorage.getUsers();
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
            return inMemoryUserStorage.addUser(user);
        }
    }
    public User updateUser(User user)throws NotFoundException, ValidationException {
        users = inMemoryUserStorage.getUsers();
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
        }
        return inMemoryUserStorage.updateUser(user);
    }

    public void deleteUser(long userID)throws NotFoundException{
        if (users.containsKey(userID)){
            throw new NotFoundException("Таково пользователя нет");
        }else {
            inMemoryUserStorage.deleteUser(userID);
        }
    }

    public Collection<User> getFriends(Long id){
        if(!inMemoryUserStorage.getUsers().containsKey(id)){
            throw new NotFoundException("Данного пользователя нет");
        } else {
            return inMemoryUserStorage.getFriends(id);
        }
    }

    public void addFriend(Long userID, Long friendID) throws ValidationException,NotFoundException {
        if (!inMemoryUserStorage.getUsers().containsKey(userID) || !inMemoryUserStorage.getUsers()
                .containsKey(friendID)) {
            throw new NotFoundException("Нет такого пользователя");
        } else {
            inMemoryUserStorage.addFriend(userID, friendID);
        }
    }

    public void deleteFriend(Long userID, Long friendID)throws ValidationException,NotFoundException{

        if (!inMemoryUserStorage.getUsers().containsKey(userID) || !inMemoryUserStorage.getUsers()
                .containsKey(friendID)) {
            throw new NotFoundException("Нет такого пользователя");
        }else{
           inMemoryUserStorage.deleteFriend(userID, friendID);
        }
    }

    public Collection<User> getCommonFriends(Long id, Long otherID) throws NotFoundException {
        return inMemoryUserStorage.getCommonFriends(id,otherID);
    }

    public User getUserByID(long id) throws NotFoundException {
        if(!inMemoryUserStorage.getUsers().containsKey(id)){
            throw new NotFoundException("Данного пользователя нет");
        } else {
            return inMemoryUserStorage.getUserByID(id);
        }
    }
}
