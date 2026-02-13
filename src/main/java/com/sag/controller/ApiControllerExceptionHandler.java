package com.sag.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerExceptionHandler {

    @ExceptionHandler({
        MethodArgumentNotValidException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String badRequestExceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest request) throws IOException {

        System.out.println(":::: error: " + ex.getLocalizedMessage() );
        String error = new String();

        if (ex.getLocalizedMessage().contains("")) {

            return error;
        }

             return ex.getLocalizedMessage();
    }

    @ExceptionHandler({
        HttpMessageNotReadableException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String badRequestExceptionHandler(HttpMessageNotReadableException ex, HttpServletRequest request) throws IOException {

        String error = ex.getMessage();
        System.out.println(":::: error: " + error );
        if (ex.getLocalizedMessage().contains("")) {

        } else if (ex.getLocalizedMessage().contains("")) {

        }

        return error;
    }

    @ExceptionHandler({
        HttpMediaTypeNotSupportedException.class
    })
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public String notAcceptableExceptionHandler(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) throws IOException {
        String error = "";
        return ex.getLocalizedMessage();

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerErrorExceptionHandler(Exception ex, HttpServletRequest request) {
        String error = "";
        return ex.getLocalizedMessage();
    }

}
