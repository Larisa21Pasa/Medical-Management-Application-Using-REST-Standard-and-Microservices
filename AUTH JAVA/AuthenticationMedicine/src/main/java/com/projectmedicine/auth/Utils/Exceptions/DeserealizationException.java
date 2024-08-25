package com.projectmedicine.auth.Utils.Exceptions;

import org.springframework.http.converter.HttpMessageNotReadableException;

import static com.projectmedicine.auth.Utils.Others.Logs.DESERIALIZATION_EXCEPTION;


public class DeserealizationException extends HttpMessageNotReadableException {
    public DeserealizationException(){ super(DESERIALIZATION_EXCEPTION);}

    public DeserealizationException(String message) {
        super(message);
    }

}
