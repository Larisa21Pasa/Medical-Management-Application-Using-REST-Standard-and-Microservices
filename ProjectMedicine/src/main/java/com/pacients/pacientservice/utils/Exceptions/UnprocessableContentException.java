package com.pacients.pacientservice.utils.Exceptions;


import static com.pacients.pacientservice.utils.Logs.ProgramLogs.UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS;

public class UnprocessableContentException extends RuntimeException {
    public UnprocessableContentException() {
        super(UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS);
    }

    public UnprocessableContentException(String message) {
        super(message);
    }
}