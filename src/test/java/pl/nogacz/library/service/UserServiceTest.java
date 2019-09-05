package pl.nogacz.library.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.nogacz.library.controller.exception.UserNotFoundException;
import pl.nogacz.library.domain.User;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    @Transactional
    public void getUser() throws UserNotFoundException {
        //Given
        User user = new User(1L, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>());
        userService.saveUser(user);

        //When
        User getUser = userService.getUser(1L);

        //Then
        assertEquals(user, getUser);
    }

    @Test
    @Transactional
    public void getUsers() {
        //Given
        userService.saveUser(new User(null, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>()));
        userService.saveUser(new User(null, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>()));
        userService.saveUser(new User(null, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>()));

        //When
        int countUsers = userService.getUsers().size();

        //Then
        assertEquals(3, countUsers);
    }

    @Test
    @Transactional
    public void saveUser() {
        //Given
        User user = new User(1L, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>());

        //When
        User saveUser = userService.saveUser(user);

        //Then
        assertEquals(user, saveUser);
    }
}