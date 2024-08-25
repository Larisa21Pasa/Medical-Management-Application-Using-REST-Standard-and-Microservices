package com.projectmedicine.auth.Utils.Exceptions;


import static com.projectmedicine.auth.Utils.Others.Logs.BAD_REQUEST_EXCEPTION;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super(BAD_REQUEST_EXCEPTION);
    }

    public BadRequestException(String message) {
        super(message);
    }
}