package org.example.prepurchase.global.error;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder errorMessages = new StringBuilder("Invalid request: ");

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMessages.append(String.format("[%s] %s, ", error.getField(), error.getDefaultMessage()))
        );

        // Remove the trailing comma and space
        errorMessages.setLength(errorMessages.length() - 2);

        return ResponseEntity.badRequest().body(errorMessages.toString());
    }
}