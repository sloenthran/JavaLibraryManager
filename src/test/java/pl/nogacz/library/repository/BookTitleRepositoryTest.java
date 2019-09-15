package pl.nogacz.library.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.nogacz.library.controller.exception.TitleNotFoundException;
import pl.nogacz.library.domain.BookTitle;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BookTitleRepositoryTest {
    @Autowired
    private BookTitleRepository bookTitleRepository;

    @Before
    public void prepareDatabase() {
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());
        bookTitleRepository.save(bookTitle);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void create() {
        //Given
        BookTitle bookTitle = new BookTitle(2L, "Test", "Test", LocalDate.now(), new ArrayList<>());

        //When
        bookTitleRepository.save(bookTitle);
        int count = bookTitleRepository.findAll().size();

        //Then
        assertEquals(2, count);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void read() throws TitleNotFoundException {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());

        //When
        BookTitle getTitle = bookTitleRepository.findById(1L).orElseThrow(TitleNotFoundException::new);

        //Then
        assertEquals(bookTitle, getTitle);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update() throws TitleNotFoundException {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "TestTwo", "Test", LocalDate.now(), new ArrayList<>());

        //When
        bookTitleRepository.save(bookTitle);
        BookTitle getTitle = bookTitleRepository.findById(1L).orElseThrow(TitleNotFoundException::new);

        //Then
        assertEquals(bookTitle, getTitle);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete() {
        //When
        bookTitleRepository.deleteById(1L);
        int count = bookTitleRepository.findAll().size();

        //Then
        assertEquals(0, count);
    }
}