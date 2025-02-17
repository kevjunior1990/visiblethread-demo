package com.example.visiblethreaddemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(TeamServiceException.class)
    public ResponseEntity<String> handleTeamServiceException(
            TeamServiceException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TeamNameNotFoundException.class)
    public ResponseEntity<String> handleTeamNameNotFoundException(
            TeamNameNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<String> handleUserServiceException(
            UserServiceException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(
            UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DocumentServiceException.class)
    public ResponseEntity<String> handleDocumentServiceException(
            DocumentServiceException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DocumentNameNotFoundException.class)
    public ResponseEntity<String> handleDocumentNameNotFoundException(
            DocumentNameNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DocumentUidNotFoundException.class)
    public ResponseEntity<String> handleDocumentUidNotFoundException(
            DocumentUidNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GeminiServiceException.class)
    public ResponseEntity<String> handleGeminiServiceException(
            GeminiServiceException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GeminiConnectorException.class)
    public ResponseEntity<String> handleGeminiConnectorException(
            GeminiConnectorException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
