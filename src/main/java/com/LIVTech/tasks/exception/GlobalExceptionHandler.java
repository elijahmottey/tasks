package com.LIVTech.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

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
}
