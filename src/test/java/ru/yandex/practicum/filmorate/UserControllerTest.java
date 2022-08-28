package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {
    Map<Integer, User> users = new HashMap<Integer, User>();
    User user = new User();

    @BeforeEach
    public void fillStorage(){
        user.setId(1);
        user.setEmail("maximpetorv@yandex.ru");
        user.setLogin("Maxim");
        user.setName("Maxim");
        user.setBirthday(LocalDate.of(1999,11,10));
        users.put(user.getId(), user);
    }

    @Test
    public void shouldReturnAllFilms() throws Exception {
        Map<Integer, User> usersTest = new HashMap<>();
        User userTest = new User();
        userTest.setId(1);
        userTest.setEmail("maximpetorv@yandex.ru");
        userTest.setLogin("Maxim");
        userTest.setName("Maxim");
        userTest.setBirthday(LocalDate.of(1999,11,10));
        usersTest.put(userTest.getId(),userTest);
        Assertions.assertEquals(usersTest, users);
        usersTest.clear();
        Assertions.assertNotEquals(usersTest, users);
        users.clear();
        Assertions.assertEquals(usersTest, users);
    }

    @Test
    public void  shouldAddFilms(){
        Map<Integer, User> usersTest = new HashMap<>();
        User userTest = new User();
        userTest.setId(1);
        userTest.setId(1);
        userTest.setEmail("maximpetorv@yandex.ru");
        userTest.setLogin("Maxim");
        userTest.setName("Maxim");
        userTest.setBirthday(LocalDate.of(1999,11,10));
        usersTest.put(userTest.getId(),userTest);
        User user1 = users.get(1);
        assertAll(
                ()->assertEquals(usersTest, users),
                ()->assertNotEquals(null, user1.getEmail()),
                ()->assertTrue(user1.getEmail().contains("@")),
                ()->assertNotEquals(null,user1.getLogin()),
                ()->assertFalse(user1.getLogin().contains(" ")),
                ()->assertEquals(userTest.getLogin(),user1.getName()),
                ()->assertTrue(user1.getBirthday().isBefore(LocalDate.now())));
    }

    @Test
    public void  shouldUpdateFilms() {
        Map<Integer, User> usersTest = new HashMap<>();
        User userTest = new User();
        userTest.setId(1);
        userTest.setId(1);
        userTest.setEmail("maximpetorv@yandex.ru");
        userTest.setLogin("Maxim");
        userTest.setName("Maxim");
        userTest.setBirthday(LocalDate.of(1999,11,10));
        usersTest.put(userTest.getId(),userTest);
        User user1 = users.get(1);
        assertAll(
                ()->assertEquals(usersTest, users),
                ()->assertNotEquals(null, user1.getEmail()),
                ()->assertTrue(user1.getEmail().contains("@")),
                ()->assertNotEquals(null,user1.getLogin()),
                ()->assertFalse(user1.getLogin().contains(" ")),
                ()->assertEquals(userTest.getLogin(),user1.getName()),
                ()->assertTrue(user1.getBirthday().isBefore(LocalDate.now())));
    }
}
