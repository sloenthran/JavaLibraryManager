package pl.nogacz.library.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nogacz.library.repository.BookTitleRepository;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/book/title", produces = "application/json")
public class BookTitleController {
    private BookTitleRepository repository;

    public BookTitleController(BookTitleRepository repository) {
        this.repository = repository;
    }

    //TODO
}
