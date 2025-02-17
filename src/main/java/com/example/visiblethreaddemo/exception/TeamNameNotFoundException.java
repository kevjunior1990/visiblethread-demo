package com.example.visiblethreaddemo.exception;

public class TeamNameNotFoundException extends RuntimeException {
    public TeamNameNotFoundException(String name) {
        super("Failed to find Team with name " + name);
    }
}
