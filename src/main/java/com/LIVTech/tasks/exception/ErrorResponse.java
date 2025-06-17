package com.LIVTech.tasks.exception;

public record ErrorResponse(
        int status,
        String message,
        String details

) {
}
