package com.devsuperior.dsvendas.controllers;

import com.devsuperior.dsvendas.dtos.ErrorMessage;
import com.devsuperior.dsvendas.exceptions.InternalServerErrorException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus
    public ErrorMessage internalServerError(InternalServerErrorException ex) {
        return new ErrorMessage(ex);
    }
    
}
