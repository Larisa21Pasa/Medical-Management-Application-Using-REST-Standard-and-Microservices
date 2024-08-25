package com.projectmedicine.gateway.gateway.Utils.Exceptions;

public class DateTimeValidationException extends RuntimeException {

    public DateTimeValidationException(String message) {
        super(message);
    }

    public DateTimeValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}