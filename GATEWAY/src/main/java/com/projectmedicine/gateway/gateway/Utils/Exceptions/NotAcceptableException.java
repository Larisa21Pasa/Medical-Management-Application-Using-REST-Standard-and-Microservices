package com.projectmedicine.gateway.gateway.Utils.Exceptions;


import static com.projectmedicine.gateway.gateway.Utils.Logs.ConsultationsLog.NOT_ACCEPTABLE_CONTENT_MESSAGE;

public class NotAcceptableException extends RuntimeException{
    public NotAcceptableException( ) {
        super(NOT_ACCEPTABLE_CONTENT_MESSAGE);
    }

    public NotAcceptableException(String message) {
        super(message);
    }
}