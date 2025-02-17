package com.example.visiblethreaddemo.exception;

public class DocumentServiceException extends RuntimeException {
    public DocumentServiceException(String message) {
        super("Document Service failed with error message:  " + message);
    }
}
