package com.example.visiblethreaddemo.exception;

public class TeamServiceException extends RuntimeException {
    public TeamServiceException(String message) {
        super("Team Service failed with error message:  " + message);
    }
}
