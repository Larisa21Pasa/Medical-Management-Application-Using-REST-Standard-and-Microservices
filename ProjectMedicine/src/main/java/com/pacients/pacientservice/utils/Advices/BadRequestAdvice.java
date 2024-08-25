package com.pacients.pacientservice.utils.Advices;

import com.pacients.pacientservice.utils.Exceptions.BadRequestException;
import com.pacients.pacientservice.utils.Exceptions.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class BadRequestAdvice {
    @ResponseBody
    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handler(BadRequestException ex) {
        return ex.getMessage();
    }
}