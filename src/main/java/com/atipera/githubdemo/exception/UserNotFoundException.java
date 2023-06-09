package com.atipera.githubdemo.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
        System.out.println("Message: " + message);
    }

}
