package com.example.phonebook.controllers;

import com.example.phonebook.exceptions.CustomResultNotFoundNotFoundException;
import com.example.phonebook.logging.LogController;
import com.example.phonebook.responses.Validations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public abstract class CustomExceptionHandler extends ResponseEntityExceptionHandler implements LogController {

    @ExceptionHandler(CustomResultNotFoundNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Validations> handleNoResourceFoundException(CustomResultNotFoundNotFoundException exception) {
        Validations validations = new Validations();
        validations.addErrorDetails(new Validations.Exceptions(
                exception.getMessage(),
                CustomResultNotFoundNotFoundException.class.getSimpleName(),
                HttpStatus.NOT_FOUND));
        getLogger().error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(validations);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Validations validations = new Validations();
        ex.getBindingResult().getAllErrors().forEach(e -> {
            getLogger().error(e.getDefaultMessage());
            Validations.Exceptions errorDetails = new Validations.Exceptions(
                    e.getDefaultMessage(),
                    MethodArgumentNotValidException.class.getSimpleName(),
                    HttpStatus.BAD_REQUEST);
            validations.addErrorDetails(errorDetails);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validations);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Validations> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        Validations validations = new Validations();
        validations.addErrorDetails(new Validations.Exceptions(
                exception.getMessage(),
                DataIntegrityViolationException.class.getSimpleName(),
                HttpStatus.BAD_REQUEST));
        getLogger().error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validations);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Validations> handleException(Exception exception) {
        Validations validations = new Validations();
        validations.addErrorDetails(new Validations.Exceptions(
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR));
        getLogger().error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(validations);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Validations validations = new Validations();
        String errorMsg = "Values can't be parsed, wrong format";
        validations.addErrorDetails(new Validations.Exceptions(
                errorMsg,
                HttpMessageNotReadableException.class.getSimpleName(),
                HttpStatus.BAD_REQUEST));
        getLogger().error(errorMsg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validations);
    }

    @Override
    public String getLoggerClassName() {
        return CustomExceptionHandler.class.getSimpleName();
    }
}

