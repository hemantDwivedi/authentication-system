package com.assignment.authenticationsystem.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> apiExceptionHandler(ApiException exception, WebRequest request){
        return new ResponseEntity<>(
                new ApiError(
                        HttpStatus.BAD_REQUEST,
                        LocalDateTime.now(),
                        List.of(exception.getMessage()),
                        request.getDescription(false)
                ),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFoundExceptionHandler(ResourceNotFoundException exception, WebRequest request){
        return new ResponseEntity<>(
                new ApiError(
                        HttpStatus.BAD_REQUEST,
                        LocalDateTime.now(),
                        List.of(exception.getMessage()),
                        request.getDescription(false)
                ),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception, WebRequest request){
        return new ResponseEntity<>(
                new ApiError(
                        HttpStatus.BAD_REQUEST,
                        LocalDateTime.now(),
                        exception.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()),
                        request.getDescription(false)
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}
