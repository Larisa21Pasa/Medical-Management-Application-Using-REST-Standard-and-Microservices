package com.projectmedicine.gateway.gateway.Utils.Exceptions;


import static com.projectmedicine.gateway.gateway.Utils.Logs.ConsultationsLog.NOT_FOUND_EXCEPTION;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super(NOT_FOUND_EXCEPTION);
    }

    public NotFoundException(String message) {
        super(message);
    }
}