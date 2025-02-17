package com.example.visiblethreaddemo.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("Failed to find User with unique identifier " + email);
    }
}
