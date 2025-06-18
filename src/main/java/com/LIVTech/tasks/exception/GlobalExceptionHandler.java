package com.LIVTech.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles IllegalArgumentException and returns a custom error response.
     *
     * @param ex      the exception that was thrown
     * @param request the web request that caused the exception
     * @return a ResponseEntity containing the error response
     */

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(
            RuntimeException ex, WebRequest request
    ){
        ErrorResponse errorResponse =  new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    /**
     * Handles {@link MethodArgumentNotValidException} which occurs when validation on an argument
     * annotated with {@code @Valid} fails.
     *
     * @param ex      The {@link MethodArgumentNotValidException} instance.
     * @param request The current web request.
     * @return A {@link ResponseEntity} with a 400 Bad Request status and a map of field errors.
     */


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        Map<String,String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }


    /**
     * Handles any other {@link Exception} not specifically caught by other handlers.
     * This acts as a catch-all for unexpected errors.
     *
     * @param ex      The {@link Exception} instance.
     * @param request The current web request.
     * @return A {@link ResponseEntity} with a 500 Internal Server Error status and a generic error message.
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleException(Exception ex,WebRequest request){
        Map<String,String> errors = new HashMap<>();
        errors.put("Message","A unexpected error occurred" + ex.getMessage());
        return new ResponseEntity<>(errors,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
