package com.manideep.skilltunerai.exception;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DuplicateValueException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateValues(DuplicateValueException exception) {
        
        logger.info("Duplicate value entered: ", exception);
        // HTTP status code: 409
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
            "message", exception.getMessage()
        ));

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleWrongCredentials(BadCredentialsException exception) {
        
        logger.warn("Wrong credentials entered: ", exception);
        // HTTP status code: 401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
            "message", exception.getMessage()
        ));
            
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(EntityNotFoundException exception) {

        logger.warn("User not found: ", exception);
        // HTTP status code: 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "message", exception.getMessage()
        ));

    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<Map<String, String>> handleErrorWhileSavingInDB(PersistenceException exception) {

        logger.error("JPA pesistence error: ", exception);
        // HTTP status code: 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "message", exception.getMessage()
        ));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleOtherExceptions(Exception exception) {

        logger.error("Unexpected error: ", exception);
        // HTTP status code: 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "message", "Our server faced an unexcepted error! Please try again later"
        ));

    }

}
