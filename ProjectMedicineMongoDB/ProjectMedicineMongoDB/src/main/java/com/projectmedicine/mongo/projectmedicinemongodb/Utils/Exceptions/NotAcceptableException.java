package com.projectmedicine.mongo.projectmedicinemongodb.Utils.Exceptions;


import static com.projectmedicine.mongo.projectmedicinemongodb.Utils.Logs.ConsultationsLog.NOT_ACCEPTABLE_CONTENT_MESSAGE;

public class NotAcceptableException extends RuntimeException{
    public NotAcceptableException( ) {
        super(NOT_ACCEPTABLE_CONTENT_MESSAGE);
    }

    public NotAcceptableException(String message) {
        super(message);
    }
}