package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exseptions.NotFoundException;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public Collection<Long> getFriends(Long id){
//        Collection<Long> friends;
        if(!inMemoryUserStorage.getUsers().containsKey(id)){
            throw new NotFoundException("Данного пользователя нет");
        }
//        else {
//            friends = inMemoryUserStorage.getUsers().get(id).getFriends();

        return inMemoryUserStorage.getUsers().get(id).getFriends();
    }

    public void addFriend(Long userID, Long friendID) throws ValidationException,NotFoundException {
        if (!inMemoryUserStorage.getUsers().containsKey(userID) || !inMemoryUserStorage.getUsers().containsKey(friendID)) {
            throw new NotFoundException("Нет такого пользователя");
        }else {
            Set<Long> friedns = new HashSet<>();
            friedns.add(friendID);
            User user = inMemoryUserStorage.getUsers().get(userID);
            user.setFriends(friedns);
            inMemoryUserStorage.updateUser(user);
        }
    }

    public void deleteFriend(Long userID, Long friendID)throws ValidationException,NotFoundException{

        if (!inMemoryUserStorage.getUsers().containsKey(userID) && !inMemoryUserStorage.getUsers().containsKey(friendID)) {
            throw new NotFoundException("Нет такого пользователя");
        }else{
            User user = inMemoryUserStorage.getUsers().get(userID);
            Set<Long> friends = user.getFriends();
            friends.remove(friendID);
            user.setFriends(friends);
            inMemoryUserStorage.updateUser(user);
        }
    }

    public Collection<Long> getCommonFriends(Long id, Long otherID) throws NotFoundException{
        Collection<Long> commondFriend = null;
        if (!inMemoryUserStorage.getUsers().containsKey(id) && (!inMemoryUserStorage.getUsers().containsKey(otherID))){
            throw new NotFoundException("Нет такого пользователя");
        }else {
            User user = inMemoryUserStorage.getUsers().get(id);
                User otherUser = inMemoryUserStorage.getUsers().get(otherID);
                for (Long friend : user.getFriends()) {
                    if (otherUser.getFriends().contains(friend))
                        commondFriend.add(friend);
                }
            }

        return commondFriend;
    }

    public User getUserByID(long id) throws NotFoundException {
        if(!inMemoryUserStorage.getUsers().containsKey(id)){
            throw new NotFoundException("Данного пользователя нет");
        } else {
           User user = inMemoryUserStorage.getUsers().get(id);
            return user;
        }
    }
}
