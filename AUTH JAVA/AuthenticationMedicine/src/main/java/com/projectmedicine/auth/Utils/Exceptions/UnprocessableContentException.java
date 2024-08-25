package com.projectmedicine.auth.Utils.Exceptions;


import static com.projectmedicine.auth.Utils.Others.Logs.UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS;

public class UnprocessableContentException extends RuntimeException {
    public UnprocessableContentException() {
        super(UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS);
    }

    public UnprocessableContentException(String message) {
        super(message);
    }
}