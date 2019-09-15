package pl.nogacz.library.mapper;

import org.springframework.stereotype.Component;
import pl.nogacz.library.controller.exception.BookNotFoundException;
import pl.nogacz.library.controller.exception.TitleNotFoundException;
import pl.nogacz.library.domain.Book;
import pl.nogacz.library.domain.BookHire;
import pl.nogacz.library.domain.BookTitle;
import pl.nogacz.library.dto.BookDto;
import pl.nogacz.library.dto.BookHireDto;
import pl.nogacz.library.dto.BookTitleDto;
import pl.nogacz.library.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    private BookService bookService;

    public BookMapper(BookService bookService) {
        this.bookService = bookService;
    }

    public BookDto mapBookToBookDto(final Book book) {
        return new BookDto(
                book.getId(),
                book.getBookTitle().getId(),
                book.getBookStatus(),
                mapListBookHireToListBookHireDto(book.getBookHires())
        );
    }

    public Book mapBookDtoToBook(final BookDto bookDto) throws TitleNotFoundException, BookNotFoundException {
        return new Book(
                bookDto.getId(),
                bookService.getBookTitle(bookDto.getTitleId()),
                bookDto.getBookStatus(),
                bookService.getBook(bookDto.getId()).getBookHires()
        );
    }

    public List<BookDto> mapListBookToListBookDto(final List<Book> books) {
        return books.stream()
                .map(this::mapBookToBookDto)
                .collect(Collectors.toList());
    }

    public BookHireDto mapBookHireToBookHireDto(final BookHire bookHire) {
        return new BookHireDto(
                bookHire.getId(),
                bookHire.getBook().getId(),
                bookHire.getUser().getId(),
                bookHire.getDateRental(),
                bookHire.getDateReturn()
        );
    }

    public List<BookHireDto> mapListBookHireToListBookHireDto(final List<BookHire> books) {
        return books.stream()
                .map(this::mapBookHireToBookHireDto)
                .collect(Collectors.toList());
    }

    public BookTitle mapBookTitleDtoToBookTitle(final BookTitleDto bookTitleDto) throws TitleNotFoundException {
        return new BookTitle(
                bookTitleDto.getId(),
                bookTitleDto.getTitle(),
                bookTitleDto.getAuthor(),
                bookTitleDto.getPublicationDate(),
                bookService.getAvailableBooksWithTitle(bookTitleDto.getTitle())
        );
    }
}
