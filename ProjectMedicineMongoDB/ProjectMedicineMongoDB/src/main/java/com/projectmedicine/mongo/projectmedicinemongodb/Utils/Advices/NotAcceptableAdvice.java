package com.projectmedicine.mongo.projectmedicinemongodb.Utils.Advices;

import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Exceptions.NotAcceptableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotAcceptableAdvice {
    @ResponseBody
    @ExceptionHandler({NotAcceptableException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String handler(NotAcceptableException ex) {
        return ex.getMessage();
    }
}
