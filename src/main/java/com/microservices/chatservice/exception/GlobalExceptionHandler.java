package com.microservices.chatservice.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoEntityFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoEntityException(NoEntityFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IllegalAttributeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalAttributeException(IllegalAttributeException e) {
        return e.getMessage();
    }

}
