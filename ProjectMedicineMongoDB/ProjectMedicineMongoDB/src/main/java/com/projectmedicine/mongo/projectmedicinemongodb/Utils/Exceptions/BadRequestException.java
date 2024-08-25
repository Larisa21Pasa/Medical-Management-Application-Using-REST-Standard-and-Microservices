package com.projectmedicine.mongo.projectmedicinemongodb.Utils.Exceptions;


import static com.projectmedicine.mongo.projectmedicinemongodb.Utils.Logs.ConsultationsLog.BAD_REQUEST_MESSAGE;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super(BAD_REQUEST_MESSAGE);
    }

    public BadRequestException(String message) {
        super(message);
    }
}