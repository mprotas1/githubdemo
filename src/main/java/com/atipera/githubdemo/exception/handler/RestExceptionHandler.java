package com.atipera.githubdemo.exception.handler;

import com.atipera.githubdemo.exception.UnsupportedMediaException;
import com.atipera.githubdemo.exception.UserNotFoundException;
import com.atipera.githubdemo.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnsupportedMediaException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidMediaType(UnsupportedMediaException exc) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), exc.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidUsername(UserNotFoundException exc) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage()), HttpStatus.NOT_FOUND);
    }
}
