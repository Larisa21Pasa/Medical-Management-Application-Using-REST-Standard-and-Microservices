package com.pacients.pacientservice.utils.Exceptions;


import static com.pacients.pacientservice.utils.Logs.ProgramLogs.NOT_FOUND_EXCEPTION;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super(NOT_FOUND_EXCEPTION);
    }

    public NotFoundException(String message) {
        super(message);
    }
}