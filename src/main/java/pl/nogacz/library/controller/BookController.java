package pl.nogacz.library.controller;

import org.springframework.web.bind.annotation.*;
import pl.nogacz.library.controller.exception.BookNotFoundException;
import pl.nogacz.library.controller.exception.BookNotInLibraryException;
import pl.nogacz.library.controller.exception.TitleNotFoundException;
import pl.nogacz.library.controller.exception.UserNotFoundException;
import pl.nogacz.library.domain.*;
import pl.nogacz.library.dto.BookDto;
import pl.nogacz.library.dto.BookTitleDto;
import pl.nogacz.library.mapper.BookMapper;
import pl.nogacz.library.service.BookService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/book", produces = "application/json")
public class BookController {
    private BookService bookService;
    private BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping(value = "addTitle", consumes = "application/json")
    public void addTitle(@RequestBody BookTitleDto bookTitle) throws TitleNotFoundException {
        bookService.addTitle(bookMapper.mapBookTitleDtoToBookTitle(bookTitle));
    }

    @GetMapping(value = "getAvailableBookStatus")
    public BookStatus[] getAvailableBookStatus() {
        return BookStatus.values();
    }

    @GetMapping(value = "getBook")
    public BookDto getBook(@RequestParam Long id) throws BookNotFoundException {
        return bookMapper.mapBookToBookDto(bookService.getBook(id));
    }

    @PutMapping(value = "updateBook", consumes = "application/json")
    public BookDto updateBook(@RequestBody BookDto book) throws TitleNotFoundException, BookNotFoundException {
        return bookMapper.mapBookToBookDto(bookService.saveBook(bookMapper.mapBookDtoToBook(book)));
    }

    @PostMapping(value = "addBook", consumes = "application/json")
    public void addBook(@RequestBody BookDto book) throws TitleNotFoundException, BookNotFoundException {
        bookService.saveBook(bookMapper.mapBookDtoToBook(book));
    }

    @GetMapping(value = "getAvailableBooksWithTitle")
    public List<BookDto> getAvailableBooksWithTitle(@RequestParam String title) throws TitleNotFoundException {
        return bookMapper.mapListBookToListBookDto(bookService.getAvailableBooksWithTitle(title));
    }

    @PostMapping(value = "rentBook", consumes = "application/json")
    public void rentBook(@RequestParam Long userId, @RequestParam Long bookId) throws UserNotFoundException, BookNotFoundException, BookNotInLibraryException {
        bookService.rentBook(userId, bookId);
    }

    @PutMapping(value = "returnBook", consumes = "application/json")
    public void returnBook(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam BookStatus bookStatus) throws BookNotFoundException {
        bookService.returnBook(userId, bookId, bookStatus);
    }
}
