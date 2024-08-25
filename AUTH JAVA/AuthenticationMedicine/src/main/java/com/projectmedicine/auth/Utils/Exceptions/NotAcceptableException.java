package com.projectmedicine.auth.Utils.Exceptions;


import static com.projectmedicine.auth.Utils.Others.Logs.NOT_ACCEPTABLE_EXCEPTION;

public class NotAcceptableException extends RuntimeException{
    public NotAcceptableException( ) {
        super(NOT_ACCEPTABLE_EXCEPTION);
    }

    public NotAcceptableException(String message) {
        super(message);
    }
}