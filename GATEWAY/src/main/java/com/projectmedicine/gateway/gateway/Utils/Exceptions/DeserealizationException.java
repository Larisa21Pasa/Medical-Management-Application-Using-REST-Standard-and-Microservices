package com.projectmedicine.gateway.gateway.Utils.Exceptions;

import org.springframework.http.converter.HttpMessageNotReadableException;

import static com.projectmedicine.gateway.gateway.Utils.Logs.ConsultationsLog.DESERIALIZATION_EXCEPTION;


public class DeserealizationException extends HttpMessageNotReadableException {
    public DeserealizationException(){ super(DESERIALIZATION_EXCEPTION);}

    public DeserealizationException(String message) {
        super(message);
    }

}
