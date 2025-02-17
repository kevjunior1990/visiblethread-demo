package com.example.visiblethreaddemo.exception;

public class DocumentNameNotFoundException extends RuntimeException {
    public DocumentNameNotFoundException(String name) {
        super("Failed to find Document with name " + name);
    }
}
