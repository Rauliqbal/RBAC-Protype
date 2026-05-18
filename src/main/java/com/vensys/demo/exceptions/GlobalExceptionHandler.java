package com.vensys.demo.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vensys.demo.DTO.responses.RestResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RestResponse<Object>> handleValidationException(
      MethodArgumentNotValidException ex) {

    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getDefaultMessage())
        .toList();

    RestResponse<Object> response = RestResponse.builder()
        .success(false)
        .message("Validation failed")
        .errors(errors)
        .build();

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(response);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<RestResponse<Object>> handleNotFoundException(NotFoundException ex) {
    RestResponse<Object> response = RestResponse.builder()
        .success(false)
        .message("Resource not found")
        .errors(List.of(ex.getMessage()))
        .build();

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(response);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<RestResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
    RestResponse<Object> response = RestResponse.builder()
        .success(false)
        .message("Invalid input")
        .errors(List.of(ex.getMessage()))
        .build();

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(response);
  }
}