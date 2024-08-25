package com.pacients.pacientservice.utils.Advices;

import com.pacients.pacientservice.utils.Exceptions.NotFoundException;
import com.pacients.pacientservice.utils.Exceptions.UnprocessableContentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UnprocessableContentAdvice {
    @ResponseBody
    @ExceptionHandler({UnprocessableContentException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handler(UnprocessableContentException ex) {
        return ex.getMessage();
    }
}
