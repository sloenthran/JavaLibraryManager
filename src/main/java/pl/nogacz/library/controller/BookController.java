package pl.nogacz.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.nogacz.library.controller.exception.BookNotFoundException;
import pl.nogacz.library.controller.exception.TitleNotFoundException;
import pl.nogacz.library.domain.Book;
import pl.nogacz.library.domain.BookStatus;
import pl.nogacz.library.domain.BookTitle;
import pl.nogacz.library.repository.BookHireRepository;
import pl.nogacz.library.repository.BookRepository;
import pl.nogacz.library.repository.BookTitleRepository;

import java.util.ArrayList;
import java.util.List;

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

    public BookController(@Autowired BookRepository bookRepository, @Autowired BookHireRepository bookHireRepository, @Autowired BookTitleRepository bookTitleRepository) {
        this.bookRepository = bookRepository;
        this.bookHireRepository = bookHireRepository;
        this.bookTitleRepository = bookTitleRepository;
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
}
