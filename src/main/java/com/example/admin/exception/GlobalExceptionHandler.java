package com.example.admin.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;


// extends ResponseEntityExceptionHandler
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleException(Throwable e) {
        log.error("", e);

        HashMap<String, String> content = new HashMap<>();
        content.put("message", e.getMessage());

        return new ResponseEntity<>(content, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        log.error("", e);

        HashMap<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(
                    fieldError.getField().replaceAll("[A-Z]", "_$0").toLowerCase(),
                    fieldError.getDefaultMessage()
            );
        });

        HashMap<String, Object> content = new HashMap<>();
        content.put("message", "参数错误");
        content.put("errors", errors);

        return new ResponseEntity<>(content, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(FieldException.class)
    public ResponseEntity<?> handleFieldException(FieldException e) {
//        log.error("", e);

        HashMap<String, Object> content = new HashMap<>();
        content.put("message", e.getMessage());
        content.put("errors", e.getErrors());

        return new ResponseEntity<>(content, HttpStatus.BAD_REQUEST);
    }
}
