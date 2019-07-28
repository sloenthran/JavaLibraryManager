package pl.nogacz.library.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nogacz.library.repository.BookHireRepository;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/book/hire", produces = "application/json")
public class BookHireController {
    private BookHireRepository repository;

    public BookHireController(BookHireRepository repository) {
        this.repository = repository;
    }
}
