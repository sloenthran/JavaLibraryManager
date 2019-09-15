package pl.nogacz.library.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.nogacz.library.controller.exception.BookNotFoundException;
import pl.nogacz.library.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BookHireRepositoryTest {
    @Autowired
    private BookHireRepository bookHireRepository;

    @Autowired
    private BookTitleRepository bookTitleRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void prepareDatabase() {
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());
        bookTitleRepository.save(bookTitle);

        Book book = new Book(1L, bookTitle, BookStatus.IN_LIBRARY, new ArrayList<>());
        bookRepository.save(book);

        User user = new User(1L, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>());
        userRepository.save(user);

        BookHire bookHire = new BookHire(1L, book, user, LocalDate.now(), null);
        bookHireRepository.save(bookHire);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void create() {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());
        Book book = new Book(1L, bookTitle, BookStatus.IN_LIBRARY, new ArrayList<>());
        User user = new User(1L, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>());

        BookHire bookHire = new BookHire(2L, book, user, LocalDate.now(), null);

        //When
        bookHireRepository.save(bookHire);
        int count = bookHireRepository.findAll().size();

        //Then
        assertEquals(2, count);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void read() throws BookNotFoundException {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());
        Book book = new Book(1L, bookTitle, BookStatus.IN_LIBRARY, new ArrayList<>());
        User user = new User(1L, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>());

        BookHire bookHire = new BookHire(1L, book, user, LocalDate.now(), null);

        //When
        BookHire getHire = bookHireRepository.findById(1L).orElseThrow(BookNotFoundException::new);

        //Then
        assertEquals(1L, getHire.getId(), 0);
        assertEquals(book.getId(), getHire.getBook().getId());
        assertEquals(user.getId(), getHire.getUser().getId());
        assertEquals(bookHire.getDateRental(), getHire.getDateRental());
        assertNull(bookHire.getDateReturn());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update() throws BookNotFoundException {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());
        Book book = new Book(1L, bookTitle, BookStatus.IN_LIBRARY, new ArrayList<>());
        User user = new User(1L, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>());

        BookHire bookHire = new BookHire(1L, book, user, LocalDate.now(), LocalDate.now());

        //When
        bookHireRepository.save(bookHire);
        BookHire getHire = bookHireRepository.findById(1L).orElseThrow(BookNotFoundException::new);

        //Then
        assertEquals(bookHire.getDateReturn(), getHire.getDateReturn());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete() {
        //When
        bookHireRepository.deleteById(1L);
        int count = bookHireRepository.findAll().size();

        //Then
        assertEquals(0, count);
    }
}