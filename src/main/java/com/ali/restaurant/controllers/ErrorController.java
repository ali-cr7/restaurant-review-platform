package com.ali.restaurant.controllers;

import com.ali.restaurant.domain.dtos.ErrorDto;
import com.ali.restaurant.exceptions.BaseException;
import com.ali.restaurant.exceptions.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {
    @ExceptionHandler
 public ResponseEntity<ErrorDto> handleStorageException (StorageException ex)
 {
     log.error("Caught StorageException",ex);
     ErrorDto errorDto = ErrorDto.builder()
             .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
             .message("Unable to save or retrieve at the time")
             .build();
     return new ResponseEntity<>(errorDto , HttpStatus.INTERNAL_SERVER_ERROR);
 }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleBaseException (BaseException ex)
    {
        log.error("Caught StorageException",ex);
        ErrorDto errorDto = ErrorDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .build();
        return new ResponseEntity<>(errorDto , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleException (Exception ex)
    {
        log.error("Caught StorageException",ex);
        ErrorDto errorDto = ErrorDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .build();
        return new ResponseEntity<>(errorDto , HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
