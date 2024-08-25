package com.projectmedicine.gateway.gateway.Utils.Exceptions;


public class UnprocessableContentException extends RuntimeException  {

    public UnprocessableContentException(String message) {
        super(message);
    }
    public int getErrorCode() {
        return 422;
    }
}