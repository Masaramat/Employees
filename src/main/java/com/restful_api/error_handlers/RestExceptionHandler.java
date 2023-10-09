package com.restful_api.error_handlers;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// This class handles all exceptions thrown in the application
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    // When data constraints violation error is thrown
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    // Tries to add data with an existing unique attribute
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        String errorMessage = ex.getMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    // Handles incorrect login credentials
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex){
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    // When user is trying to log in with non-existing username/email
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex){
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex){
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex){
        String message = ex.getMessage();

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException ex){
        return ResponseEntity.status(401).body(ex.getMessage());
    }
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<String> handleServletException(ServletException ex){
        return ResponseEntity.status(401).body(ex.getMessage());
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex){
        return ResponseEntity.status(401).body(ex.getMessage());
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwtException(MalformedJwtException ex){
        return ResponseEntity.status(401).body(ex.getMessage());
    }



}
