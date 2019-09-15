package pl.nogacz.library.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.nogacz.library.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Before
    public void prepareDatabase() {
        User user = new User(1L, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>());
        userRepository.save(user);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void create() {
        //Given
        User user = new User(2L, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>());

        //When
        userRepository.save(user);
        int count = userRepository.findAll().size();

        //Then
        assertEquals(2, count);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void read() {
        //Given
        User user = new User(1L, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>());

        //When
        User getUser = userRepository.findById(1L).orElse(null);

        //Then
        assertEquals(user, getUser);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update() {
        //Given
        User user = new User(1L, "Mateusz", "Wolniewicz", LocalDate.now(), new ArrayList<>());

        //When
        userRepository.save(user);
        User getUser = userRepository.findById(1L).orElse(null);

        //Then
        assertEquals(user, getUser);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete() {
        //When
        userRepository.deleteById(1L);
        int count = userRepository.findAll().size();

        //Then
        assertEquals(0, count);
    }
}