package com.almacen.api.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiError {

    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private Object errors;

    public ApiError(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(int status, String error, String message, Object errors) {
        this(status, error, message);
        this.errors = errors;
    }
}
