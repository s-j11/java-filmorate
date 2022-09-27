package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exseptions.NotFoundException;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

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

    public Collection<User> getFriends(Long id){
      Collection<Long> friendsID;
      Collection<User> friends = new ArrayList<>();
        if(!inMemoryUserStorage.getUsers().containsKey(id)){
            throw new NotFoundException("Данного пользователя нет");
        } else {
            friendsID = inMemoryUserStorage.getUsers().get(id).getFriends();
            for (Long number : friendsID){
                User user = inMemoryUserStorage.getUsers().get(number);
                friends.add(user);
            }
            return friends;
        }
    }

    public void addFriend(Long userID, Long friendID) throws ValidationException,NotFoundException {
        if (!inMemoryUserStorage.getUsers().containsKey(userID) || !inMemoryUserStorage.getUsers()
                .containsKey(friendID)) {
            throw new NotFoundException("Нет такого пользователя");
        } else {
            Set<Long> friedns = new HashSet<>();
            Set<Long> friends2 = new HashSet<>();
            User user = inMemoryUserStorage.getUsers().get(userID);
            User otherUser = inMemoryUserStorage.getUsers().get(friendID);
            if (inMemoryUserStorage.getUsers().get(userID).getFriends() == null) {
                friedns.add(friendID);
                user.setFriends(friedns);
                inMemoryUserStorage.updateUser(user);
                friends2.add(userID);
                otherUser.setFriends(friends2);
                inMemoryUserStorage.updateUser(otherUser);
            }else if (inMemoryUserStorage.getUsers().get(userID).getFriends() != null) {
                friedns = user.getFriends();
                friedns.add(friendID);
                user.setFriends(friedns);
                inMemoryUserStorage.updateUser(user);
                friends2.add(userID);
                otherUser.setFriends(friends2);
                inMemoryUserStorage.updateUser(otherUser);
            }else if (inMemoryUserStorage.getUsers().get(userID).getFriends() != null || inMemoryUserStorage.getUsers()
                    .get(friendID).getFriends() != null) {
                    friedns = user.getFriends();
                    friedns.add(friendID);
                    user.setFriends(friedns);
                    inMemoryUserStorage.updateUser(user);
                    friends2 = otherUser.getFriends();
                    friends2.add(userID);
                    otherUser.setFriends(friends2);
                    inMemoryUserStorage.updateUser(otherUser);
                }
            }
        }

    public void deleteFriend(Long userID, Long friendID)throws ValidationException,NotFoundException{

        if (!inMemoryUserStorage.getUsers().containsKey(userID) || !inMemoryUserStorage.getUsers()
                .containsKey(friendID)) {
            throw new NotFoundException("Нет такого пользователя");
        }else{
            User user = inMemoryUserStorage.getUsers().get(userID);
            User friend = inMemoryUserStorage.getUsers().get(friendID);
            Set<Long> friends = user.getFriends();
            Set<Long> friends2 = friend.getFriends();
            friends.remove(friendID);
            user.setFriends(friends);
            friends2.remove(userID);
            friend.setFriends(friends2);
            inMemoryUserStorage.updateUser(user);
            inMemoryUserStorage.updateUser(friend);
        }
    }

    public Collection<User> getCommonFriends(Long id, Long otherID) throws NotFoundException {
        Collection<User> commondFriend = new ArrayList<>();
        if (inMemoryUserStorage.getUsers().containsKey(id) || (inMemoryUserStorage.getUsers().containsKey(otherID))) {
            User user = inMemoryUserStorage.getUsers().get(id);
            User otherUser = inMemoryUserStorage.getUsers().get(otherID);
            if(user.getFriends()== null || otherUser.getFriends()==null) {
            return commondFriend;
                //if (!user.getFriends().isEmpty() || !otherUser.getFriends().isEmpty())
            } else  {
                    for (Long friend : user.getFriends()) {
                        if (otherUser.getFriends().contains(friend)) {
                            commondFriend.add(inMemoryUserStorage.getUsers().get(friend));
                        }
                    }
                    return commondFriend;
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
