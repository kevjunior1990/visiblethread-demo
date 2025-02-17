package com.example.visiblethreaddemo.exception;

public class GeminiServiceException extends RuntimeException {
    public GeminiServiceException(String message) {
        super("Gemini Service failed with error message: " + message);
    }
}
