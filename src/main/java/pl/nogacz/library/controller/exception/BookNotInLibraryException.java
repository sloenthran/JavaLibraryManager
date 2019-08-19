package pl.nogacz.library.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dawid Nogacz on 19.08.2019
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Book is not in library")
public class BookNotInLibraryException extends Exception {
}
