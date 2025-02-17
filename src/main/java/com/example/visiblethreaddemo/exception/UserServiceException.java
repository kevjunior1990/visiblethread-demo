package com.example.visiblethreaddemo.exception;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super("User Service failed with error message:  " + message);
    }
}
