package pl.nogacz.library.controller;

import org.springframework.web.bind.annotation.*;
import pl.nogacz.library.controller.exception.BookNotFoundException;
import pl.nogacz.library.controller.exception.BookNotInLibraryException;
import pl.nogacz.library.controller.exception.TitleNotFoundException;
import pl.nogacz.library.controller.exception.UserNotFoundException;
import pl.nogacz.library.domain.*;
import pl.nogacz.library.repository.BookHireRepository;
import pl.nogacz.library.repository.BookRepository;
import pl.nogacz.library.repository.BookTitleRepository;
import pl.nogacz.library.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/book", produces = "application/json")
public class BookController {
    private BookRepository bookRepository;
    private BookHireRepository bookHireRepository;
    private BookTitleRepository bookTitleRepository;
    private UserRepository userRepository;

    public BookController(
            BookRepository bookRepository,
            BookHireRepository bookHireRepository,
            BookTitleRepository bookTitleRepository,
            UserRepository userRepository
    ) {
        this.bookRepository = bookRepository;
        this.bookHireRepository = bookHireRepository;
        this.bookTitleRepository = bookTitleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "addTitle", consumes = "application/json")
    public void addTitle(@RequestBody BookTitle bookTitle) {
        bookTitleRepository.save(bookTitle);
    }

    @GetMapping(value = "getAvailableBookStatus")
    public BookStatus[] getAvailableBookStatus() {
        return BookStatus.values();
    }

    @GetMapping(value = "getBook")
    public Book getBook(@RequestParam Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @PutMapping(value = "updateBook", consumes = "application/json")
    public Book updateBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PostMapping(value = "addBook", consumes = "application/json")
    public void addBook(@RequestBody Book book) {
        bookRepository.save(book);
    }

    @GetMapping(value = "getAvailableBooksWithTitle")
    public List<Book> getAvailableBooksWithTitle(@RequestParam String title) throws TitleNotFoundException {
        BookTitle bookTitle = bookTitleRepository.findByTitleLike(title).orElseThrow(TitleNotFoundException::new);

        List<Book> availableBooks = new ArrayList<>();

        for(Book book : bookTitle.getBooks()) {
            if(book.getBookStatus().equals(BookStatus.IN_LIBRARY)) {
                availableBooks.add(book);
            }
        }

        return availableBooks;
    }

    @PostMapping(value = "rentBook", consumes = "application/json")
    public void rentBook(@RequestParam Long userId, @RequestParam Long bookId) throws Exception {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if(book.getBookStatus().equals(BookStatus.IN_LIBRARY)) {
            BookHire bookHire = new BookHire();
            bookHire.setBook(book);
            bookHire.setUser(user);
            bookHire.setDateRental(LocalDate.now());
            bookHire.setDateReturn(LocalDate.now().plusDays(30));

            bookHireRepository.save(bookHire);

            book.setBookStatus(BookStatus.LOANED);

            bookRepository.save(book);
        } else {
            throw new BookNotInLibraryException();
        }
    }

    @DeleteMapping(value = "returnBook", consumes = "application/json")
    public void returnBook(@RequestParam Long userId, @RequestParam Long bookId) throws BookNotFoundException {
        BookHire bookHire = bookHireRepository.findByUser_IdAndBook_Id(userId, bookId).orElseThrow(BookNotFoundException::new);
        bookHireRepository.delete(bookHire);
    }
}
