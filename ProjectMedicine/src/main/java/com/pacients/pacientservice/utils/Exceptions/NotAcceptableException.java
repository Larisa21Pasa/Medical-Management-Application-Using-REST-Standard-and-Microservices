package com.pacients.pacientservice.utils.Exceptions;


import static com.pacients.pacientservice.utils.Logs.ProgramLogs.NOT_ACCEPTABLE_EXCEPTION;

public class NotAcceptableException extends RuntimeException{
    public NotAcceptableException( ) {
        super(NOT_ACCEPTABLE_EXCEPTION);
    }

    public NotAcceptableException(String message) {
        super(message);
    }
}