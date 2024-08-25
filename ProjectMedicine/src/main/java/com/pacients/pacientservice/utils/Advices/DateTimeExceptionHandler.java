package com.pacients.pacientservice.utils.Advices;

import com.pacients.pacientservice.utils.Exceptions.DateTimeValidationException;
import com.pacients.pacientservice.utils.Exceptions.UnprocessableContentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DateTimeExceptionHandler {
    @ResponseBody
    @ExceptionHandler(DateTimeValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handler(DateTimeValidationException ex) {
        return ex.getMessage();
    }

}
