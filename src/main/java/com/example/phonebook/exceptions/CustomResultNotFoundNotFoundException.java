package com.example.phonebook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomResultNotFoundNotFoundException extends RuntimeException {
    public CustomResultNotFoundNotFoundException(String message) {
        super(message);
    }
}