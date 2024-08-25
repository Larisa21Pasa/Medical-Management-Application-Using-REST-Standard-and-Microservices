package com.projectmedicine.auth.Utils.Advices;

import com.projectmedicine.auth.Utils.Exceptions.DateTimeValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DateTimeExceptionHandler {
    @ExceptionHandler(DateTimeValidationException.class)
    public ResponseEntity<String> handleDateTimeValidationException(DateTimeValidationException ex) {
        // Customize the response message or structure as needed
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
