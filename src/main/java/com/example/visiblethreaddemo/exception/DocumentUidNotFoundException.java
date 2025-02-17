package com.example.visiblethreaddemo.exception;

public class DocumentUidNotFoundException extends RuntimeException {
    public DocumentUidNotFoundException(Long uid) {
        super("Failed to find with Document with unique identifier " + uid);
    }
}
