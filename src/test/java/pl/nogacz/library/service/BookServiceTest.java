package pl.nogacz.library.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.nogacz.library.controller.exception.BookNotFoundException;
import pl.nogacz.library.controller.exception.BookNotInLibraryException;
import pl.nogacz.library.controller.exception.TitleNotFoundException;
import pl.nogacz.library.controller.exception.UserNotFoundException;
import pl.nogacz.library.domain.Book;
import pl.nogacz.library.domain.BookStatus;
import pl.nogacz.library.domain.BookTitle;
import pl.nogacz.library.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addTitle() {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());

        //When
        BookTitle saveTitle = bookService.addTitle(bookTitle);

        //Then
        assertEquals(bookTitle, saveTitle);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getBook() throws BookNotFoundException {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());

        bookService.addTitle(bookTitle);

        Book book = new Book(1L, bookTitle, BookStatus.IN_LIBRARY, new ArrayList<>());

        bookService.saveBook(book);

        //When
        Book getBook = bookService.getBook(1L);

        //Then
        assertEquals(book.getId(), getBook.getId());
        assertEquals(bookTitle.getId(), getBook.getBookTitle().getId());
        assertEquals(book.getBookStatus(), BookStatus.IN_LIBRARY);
        assertEquals(0, getBook.getBookHires().size());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void saveBook() {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());

        bookService.addTitle(bookTitle);

        Book book = new Book(1L, bookTitle, BookStatus.IN_LIBRARY, new ArrayList<>());

        //When
        Book saveBook = bookService.saveBook(book);

        //Then
        assertEquals(book.getId(), saveBook.getId());
        assertEquals(bookTitle.getId(), saveBook.getBookTitle().getId());
        assertEquals(book.getBookStatus(), BookStatus.IN_LIBRARY);
        assertEquals(0, saveBook.getBookHires().size());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getAvailableBooksWithTitle() throws TitleNotFoundException {
        //Given
        BookTitle bookTitleOne = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());
        BookTitle bookTitleTwo = new BookTitle(2L, "Nope", "Nope", LocalDate.now(), new ArrayList<>());


        bookService.addTitle(bookTitleOne);
        bookService.addTitle(bookTitleTwo);

        Book bookOne = new Book(1L, bookTitleOne, BookStatus.IN_LIBRARY, new ArrayList<>());
        Book bookTwo = new Book(2L, bookTitleOne, BookStatus.IN_LIBRARY, new ArrayList<>());
        Book bookThree = new Book(3L, bookTitleTwo, BookStatus.IN_LIBRARY, new ArrayList<>());

        bookService.saveBook(bookOne);
        bookService.saveBook(bookTwo);
        bookService.saveBook(bookThree);

        //When
        int countTitleOne = bookService.getAvailableBooksWithTitle("Test").size();
        int countTitleTwo = bookService.getAvailableBooksWithTitle("Nope").size();

        //Then
        assertEquals(2, countTitleOne);
        assertEquals(1, countTitleTwo);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void rentBook() throws UserNotFoundException, BookNotFoundException, BookNotInLibraryException {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());

        bookService.addTitle(bookTitle);

        Book book = new Book(1L, bookTitle, BookStatus.IN_LIBRARY, new ArrayList<>());

        bookService.saveBook(book);

        User user = new User(1L, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>());

        userService.saveUser(user);

        //When
        BookStatus bookStatusBeforeRent = bookService.getBook(1L).getBookStatus();
        bookService.rentBook(user.getId(), book.getId());
        BookStatus bookStatusAfterRent = bookService.getBook(1L).getBookStatus();

        //Then
        assertEquals(BookStatus.IN_LIBRARY, bookStatusBeforeRent);
        assertEquals(BookStatus.LOANED, bookStatusAfterRent);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void returnBook() throws UserNotFoundException, BookNotFoundException, BookNotInLibraryException {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test", "Test", LocalDate.now(), new ArrayList<>());

        bookService.addTitle(bookTitle);

        Book book = new Book(1L, bookTitle, BookStatus.IN_LIBRARY, new ArrayList<>());

        bookService.saveBook(book);

        User user = new User(1L, "Dawid", "Nogacz", LocalDate.now(), new ArrayList<>());

        userService.saveUser(user);

        bookService.rentBook(1L, 1L);

        //When
        BookStatus bookStatusBeforeReturn = bookService.getBook(1L).getBookStatus();
        bookService.returnBook(1L, 1L, BookStatus.DESTROYED);
        BookStatus bookStatusAfterReturn = bookService.getBook(1L).getBookStatus();

        //Then
        assertEquals(BookStatus.LOANED, bookStatusBeforeReturn);
        assertEquals(BookStatus.DESTROYED, bookStatusAfterReturn);
    }
}