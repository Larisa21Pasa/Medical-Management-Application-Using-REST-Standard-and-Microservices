package com.pacients.pacientservice.utils.Exceptions;

import org.springframework.http.converter.HttpMessageNotReadableException;

import static com.pacients.pacientservice.utils.Logs.ProgramLogs.DESERIALIZATION_EXCEPTION;


public class DeserealizationException extends HttpMessageNotReadableException {
    public DeserealizationException(){ super(DESERIALIZATION_EXCEPTION);}

    public DeserealizationException(String message) {
        super(message);
    }

}
