package com.atipera.githubdemo.exception.handler;

import com.atipera.githubdemo.exception.UserNotFoundException;
import com.atipera.githubdemo.response.error.ErrorResponse;
import org.springframework.http.*;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidUsername(UserNotFoundException exc) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                exc.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        ErrorResponse response = new ErrorResponse(ex.getStatusCode().value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

}
