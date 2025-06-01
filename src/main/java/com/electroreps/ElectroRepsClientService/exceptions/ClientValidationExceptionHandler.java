package com.electroreps.ElectroRepsClientService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Log the exception or handle it as needed
        // For simplicity, we return a string message here
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed, please send a valid Client entity as request body ");
    }
}
