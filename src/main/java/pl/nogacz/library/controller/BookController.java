package pl.nogacz.library.controller;

import org.springframework.web.bind.annotation.*;
import pl.nogacz.library.controller.exception.BookNotFoundException;
import pl.nogacz.library.controller.exception.BookNotInLibraryException;
import pl.nogacz.library.controller.exception.TitleNotFoundException;
import pl.nogacz.library.controller.exception.UserNotFoundException;
import pl.nogacz.library.domain.*;
import pl.nogacz.library.service.BookService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/book", produces = "application/json")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "addTitle", consumes = "application/json")
    public void addTitle(@RequestBody BookTitle bookTitle) {
        bookService.addTitle(bookTitle);
    }

    @GetMapping(value = "getAvailableBookStatus")
    public BookStatus[] getAvailableBookStatus() {
        return BookStatus.values();
    }

    @GetMapping(value = "getBook")
    public Book getBook(@RequestParam Long id) throws BookNotFoundException {
        return bookService.getBook(id);
    }

    @PutMapping(value = "updateBook", consumes = "application/json")
    public Book updateBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PostMapping(value = "addBook", consumes = "application/json")
    public void addBook(@RequestBody Book book) {
        bookService.saveBook(book);
    }

    @GetMapping(value = "getAvailableBooksWithTitle")
    public List<Book> getAvailableBooksWithTitle(@RequestParam String title) throws TitleNotFoundException {
        return bookService.getAvailableBooksWithTitle(title);
    }

    @PostMapping(value = "rentBook", consumes = "application/json")
    public void rentBook(@RequestParam Long userId, @RequestParam Long bookId) throws UserNotFoundException, BookNotFoundException, BookNotInLibraryException {
        bookService.rentBook(userId, bookId);
    }

    @DeleteMapping(value = "returnBook", consumes = "application/json")
    public void returnBook(@RequestParam Long userId, @RequestParam Long bookId) throws BookNotFoundException {
        bookService.returnBook(userId, bookId);
    }
}
