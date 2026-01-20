package com.almacen.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                new ApiError(400, "Bad Request", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // 403 - Forbidden
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleForbidden(AccessDeniedException ex) {
        return new ResponseEntity<>(
                new ApiError(403, "Forbidden", "No tiene permisos para acceder a este recurso"),
                HttpStatus.FORBIDDEN);
    }

    // 404 - Not Found
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleNotFound(RuntimeException ex) {
        return new ResponseEntity<>(
                new ApiError(404, "NO ENCONTRADO", ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    // 500 - Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleInternal(Exception ex) {
        return new ResponseEntity<>(
                new ApiError(500, "ERROR INTERNO", "Ocurrio un error inesperado"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
