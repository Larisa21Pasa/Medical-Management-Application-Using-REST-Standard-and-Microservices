package com.projectmedicine.mongo.projectmedicinemongodb.Utils.Exceptions;


import org.springframework.http.converter.HttpMessageNotReadableException;

public class UnprocessableContentException extends RuntimeException  {

    public UnprocessableContentException(String message) {
        super(message);
    }
}