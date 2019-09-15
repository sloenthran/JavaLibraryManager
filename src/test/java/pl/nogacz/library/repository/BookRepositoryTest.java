package pl.nogacz.library.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.nogacz.library.controller.exception.BookNotFoundException;
import pl.nogacz.library.domain.Book;
import pl.nogacz.library.domain.BookStatus;
import pl.nogacz.library.domain.BookTitle;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookTitleRepository bookTitleRepository;

    @Before
    public void prepareDatabase() {
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());

        bookTitleRepository.save(bookTitle);

        Book book = new Book(1L, bookTitle, BookStatus.IN_LIBRARY, new ArrayList<>());

        bookRepository.save(book);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void create() {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());

        Book book = new Book(2L, bookTitle, BookStatus.IN_LIBRARY, new ArrayList<>());

        //When
        bookRepository.save(book);
        int count = bookRepository.findAll().size();

        //Then
        assertEquals(2, count);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void read() throws BookNotFoundException {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());

        Book book = new Book(1L, bookTitle, BookStatus.IN_LIBRARY, new ArrayList<>());

        //When
        Book getBook = bookRepository.findById(1L).orElseThrow(BookNotFoundException::new);

        //Then
        assertEquals(book.getId(), getBook.getId());
        assertEquals(bookTitle.getId(), getBook.getBookTitle().getId());
        assertEquals(book.getBookStatus(), BookStatus.IN_LIBRARY);
        assertEquals(0, getBook.getBookHires().size());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update() throws BookNotFoundException {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());

        Book book = new Book(1L, bookTitle, BookStatus.DESTROYED, new ArrayList<>());

        //When
        bookRepository.save(book);
        Book getBook = bookRepository.findById(1L).orElseThrow(BookNotFoundException::new);

        //Then
        assertEquals(BookStatus.DESTROYED, getBook.getBookStatus());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete() {
        //When
        bookRepository.deleteById(1L);
        int count = bookRepository.findAll().size();

        //Then
        assertEquals(0, count);
    }
}