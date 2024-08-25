package com.projectmedicine.auth.Utils.Exceptions;


import static com.projectmedicine.auth.Utils.Others.Logs.NOT_FOUND_EXCEPTION;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super(NOT_FOUND_EXCEPTION);
    }

    public NotFoundException(String message) {
        super(message);
    }
}