package pl.nogacz.library.service;

import org.springframework.stereotype.Service;
import pl.nogacz.library.controller.exception.BookNotFoundException;
import pl.nogacz.library.controller.exception.BookNotInLibraryException;
import pl.nogacz.library.controller.exception.TitleNotFoundException;
import pl.nogacz.library.controller.exception.UserNotFoundException;
import pl.nogacz.library.domain.*;
import pl.nogacz.library.repository.BookHireRepository;
import pl.nogacz.library.repository.BookRepository;
import pl.nogacz.library.repository.BookTitleRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookService {
    private BookRepository bookRepository;
    private BookHireRepository bookHireRepository;
    private BookTitleRepository bookTitleRepository;
    private UserService userService;

    public BookService(BookRepository bookRepository, BookHireRepository bookHireRepository, BookTitleRepository bookTitleRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.bookHireRepository = bookHireRepository;
        this.bookTitleRepository = bookTitleRepository;
        this.userService = userService;
    }

    public BookTitle addTitle(BookTitle bookTitle) {
        return bookTitleRepository.save(bookTitle);
    }

    public Book getBook(Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    public BookTitle getBookTitle(Long id) throws TitleNotFoundException {
        return bookTitleRepository.findById(id).orElseThrow(TitleNotFoundException::new);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAvailableBooksWithTitle(String title) {
        BookTitle bookTitle = bookTitleRepository.findByTitleLike(title).orElse(null);

        List<Book> availableBooks = new ArrayList<>();

        if(bookTitle != null) {
            for (Book book : bookTitle.getBooks()) {
                if (book.getBookStatus().equals(BookStatus.IN_LIBRARY)) {
                    availableBooks.add(book);
                }
            }
        }

        return availableBooks;
    }

    public void rentBook(Long userId, Long bookId) throws UserNotFoundException, BookNotFoundException, BookNotInLibraryException {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        User user = userService.getUser(userId);

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

    public void returnBook(Long userId, Long bookId, BookStatus bookStatus) throws BookNotFoundException {
        BookHire bookHire = bookHireRepository.findByUser_IdAndBook_Id(userId, bookId).orElseThrow(BookNotFoundException::new);

        Book book = bookHire.getBook();
        book.setBookStatus(bookStatus);

        bookHire.setDateReturn(LocalDate.now());

        bookRepository.save(book);
        bookHireRepository.save(bookHire);
    }
}