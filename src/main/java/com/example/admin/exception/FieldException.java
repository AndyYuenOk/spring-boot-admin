package com.example.admin.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class FieldException extends RuntimeException {
    private final Map<String, Object> errors;

    public FieldException(Map<String, Object> errors) {
        super(HttpStatus.BAD_REQUEST.getReasonPhrase());
        this.errors = errors;
    }

    public FieldException(String message, Map<String, Object> errors) {
        super(message);
        this.errors = errors;
    }
}
