package com.manideep.skilltunerai.exception;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.genai.errors.ApiException;

import io.jsonwebtoken.io.IOException;
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

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, String>> handleAccessDenied(SecurityException exception) {
        
        logger.error("Access denied to use this operation: ", exception);
        // HTTP status code: 403
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
            "message", exception.getMessage()
        ));
            
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(EntityNotFoundException exception) {

        logger.warn("Data not found: ", exception);
        // HTTP status code: 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "message", exception.getMessage()
        ));

    }
    
    @ExceptionHandler({FileLoadingException.class, IllegalArgumentException.class})
    public ResponseEntity<Map<String, String>> handleCorruptedFileLoaded(Exception exception) {

        logger.warn("Wrong extention or corrupted file: ", exception);
        // HTTP status code: 406
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
            "message", exception.getMessage()
        ));

    }

    @ExceptionHandler({IOException.class, FileUploadException.class})
    public ResponseEntity<Map<String, String>> handleIOException(Exception exception) {

        logger.error("Unable to read or upload the file: ", exception);
        // HTTP status code: 400
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
            "message", exception.getMessage()
        ));

    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, String>> apiExhaustedException(ApiException exception) {

        // HTTP status code: 429
        HttpStatus resStatus = HttpStatus.TOO_MANY_REQUESTS;
        if (exception.code() == 403) {
            logger.error("Gemini model access permission issue: ", exception);
            // HTTP status code: 500
            resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        else 
            logger.error("All Gemini models are exhausted: ", exception);
        
        return ResponseEntity.status(resStatus).body(Map.of(
            "message", exception.getMessage()
        ));

    }

    @ExceptionHandler(JwtExpiredException.class)
    public ResponseEntity<Map<String, String>> handleJwtTokenExpiration(Exception exception) {

        logger.info("JWT token is expired: ", exception);
        // HTTP status code: 401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
            "message", exception.getMessage()
        ));

    }

    @ExceptionHandler({PersistenceException.class, JacksonParsingException.class, CloudFileNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleErrorWhileSavingInDB(Exception exception) {

        // HTTP status code: 500
        if (exception instanceof PersistenceException) {
            logger.error("JPA pesistence error: ", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "message", exception.getMessage()
                ));
            }
        else if (exception instanceof CloudFileNotFoundException) {
            logger.error("Fail to fetch from Cloudinary: ", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "message", exception.getMessage()
                ));
        }
        else if (exception instanceof JacksonParsingException) {
            logger.error("Error while parsing from JSON to DTO: ", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "message", exception.getMessage()
            ));
        }

        logger.error("Unknown error: ", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "message", "An unknown error occured! Please try again later"
        ));

    }

    public ResponseEntity<Map<String, String>> handleOtherExceptions(Exception exception) {

        logger.error("Unexpected error: ", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "message", "Our server faced an unexpected error! Please try again later"
        ));

    }

}
