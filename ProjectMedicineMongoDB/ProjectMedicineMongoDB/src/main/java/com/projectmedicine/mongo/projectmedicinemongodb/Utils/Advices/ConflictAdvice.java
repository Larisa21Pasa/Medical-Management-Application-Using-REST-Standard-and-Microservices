package com.projectmedicine.mongo.projectmedicinemongodb.Utils.Advices;

import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Exceptions.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConflictAdvice {
    @ResponseBody
    @ExceptionHandler({ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handler(ConflictException ex) {
        return ex.getMessage();
    }
}