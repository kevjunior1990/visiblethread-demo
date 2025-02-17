package com.example.visiblethreaddemo.exception;

public class GeminiConnectorException extends RuntimeException {
    public GeminiConnectorException(String message) {
        super("Gemini Connector failed with error message: " + message);
    }
}
