package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private long id = 1;
    private Map<Long, User> users = new HashMap<>();

    public Map<Long, User> getUsers() {
        return users;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> inventoryUsers = new ArrayList<>();
        for (Map.Entry<Long, User> entry : users.entrySet()) {
            inventoryUsers.add(entry.getValue());
        }
        return inventoryUsers;
    }

    @Override
    public User addUser(User user) {
        user.setId(id);
        users.put(user.getId(), user);
        id++;
        return user;
    }

    @Override
    public void deleteUser(long userID) {
        users.remove(userID);

    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public User getUserByID(long id) {
        return getUsers().get(id);
    }

    public Collection<User> getFriends(Long id) {
        Collection<Long> friendsID;
        Collection<User> friends = new ArrayList<>();
        friendsID = getUsers().get(id).getFriends();
        for (Long number : friendsID) {
            User user = getUsers().get(number);
            friends.add(user);
        }
        return friends;
    }

    public void addFriend(Long userID, Long friendID) {
        Set<Long> friedns = new HashSet<>();
        Set<Long> friends2 = new HashSet<>();
        User user = getUsers().get(userID);
        User otherUser = getUsers().get(friendID);
        if (getUsers().get(userID).getFriends() == null) {
            friedns.add(friendID);
            user.setFriends(friedns);
            updateUser(user);
            friends2.add(userID);
            otherUser.setFriends(friends2);
            updateUser(otherUser);
        } else if (getUsers().get(userID).getFriends() != null) {
            friedns = user.getFriends();
            friedns.add(friendID);
            user.setFriends(friedns);
            updateUser(user);
            friends2.add(userID);
            otherUser.setFriends(friends2);
            updateUser(otherUser);
        } else if (getUsers().get(userID).getFriends() != null || getUsers()
                .get(friendID).getFriends() != null) {
            friedns = user.getFriends();
            friedns.add(friendID);
            user.setFriends(friedns);
            updateUser(user);
            friends2 = otherUser.getFriends();
            friends2.add(userID);
            otherUser.setFriends(friends2);
            updateUser(otherUser);
        }
    }

    public void deleteFriend(Long userID, Long friendID) {
        User user = getUsers().get(userID);
        User friend = getUsers().get(friendID);
        Set<Long> friends = user.getFriends();
        Set<Long> friends2 = friend.getFriends();
        friends.remove(friendID);
        user.setFriends(friends);
        friends2.remove(userID);
        friend.setFriends(friends2);
        updateUser(user);
        updateUser(friend);
    }

    public Collection<User> getCommonFriends(Long id, Long otherID) {
    Collection<User> commondFriend = new ArrayList<>();
        if(getUsers().containsKey(id) ||(getUsers().containsKey(otherID)))
    {
        User user = getUsers().get(id);
        User otherUser = getUsers().get(otherID);
        if (user.getFriends() == null || otherUser.getFriends() == null) {
            return commondFriend;
        } else {
            for (Long friend : user.getFriends()) {
                if (otherUser.getFriends().contains(friend)) {
                    commondFriend.add(getUsers().get(friend));
                }
            }
            return commondFriend;
        }
    }
        return commondFriend;
    }
}
