package com.pacients.pacientservice.utils.Exceptions;


import static com.pacients.pacientservice.utils.Logs.ProgramLogs.BAD_REQUEST_EXCEPTION;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super(BAD_REQUEST_EXCEPTION);
    }

    public BadRequestException(String message) {
        super(message);
    }
}