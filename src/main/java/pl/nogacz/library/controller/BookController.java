package pl.nogacz.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nogacz.library.repository.BookRepository;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/book", produces = "application/json")
public class BookController {
    private BookRepository bookRepository;

    public BookController(@Autowired BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
