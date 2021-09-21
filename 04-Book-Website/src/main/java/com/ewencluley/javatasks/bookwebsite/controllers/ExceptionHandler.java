package com.ewencluley.javatasks.bookwebsite.controllers;

import com.ewencluley.javatasks.springboot.model.BookNotFoundException;
import com.ewencluley.javatasks.springboot.model.Error;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(BookNotFoundException.class)
    public Error bookNotFound(String b) {
        return new Error(b);
    }

    public Error invalidParameterValue(String invalidParameterValue) {
        return new Error(invalidParameterValue);
    }
}
