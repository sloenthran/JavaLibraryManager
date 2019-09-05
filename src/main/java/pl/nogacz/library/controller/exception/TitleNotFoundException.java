package pl.nogacz.library.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Book with this title is not existing")
public class TitleNotFoundException extends Exception {}
