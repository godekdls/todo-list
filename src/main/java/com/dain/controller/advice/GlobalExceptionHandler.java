package com.dain.controller.advice;

import com.dain.controller.model.ErrorResponse;
import com.dain.exception.InvalidReferenceException;
import com.dain.exception.NotClosableException;
import com.dain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse _400(Exception ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler({InvalidReferenceException.class, NotClosableException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse _403(Exception ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse _404(NotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse _500(RuntimeException ex) {
        return new ErrorResponse(ex.getMessage());
    }

}
