package com.increff.pos.controller;


import com.increff.pos.commons.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    // This method is called whenever an ApiException is thrown from any controller
    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Sets the HTTP response status to 400
    public String handleApiException(ApiException e) {
        // The return value is the body of the HTTP response
        return e.getMessage();
    }
}