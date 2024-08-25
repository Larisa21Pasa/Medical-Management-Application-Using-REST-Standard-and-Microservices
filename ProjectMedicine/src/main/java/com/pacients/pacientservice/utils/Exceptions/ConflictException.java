package com.pacients.pacientservice.utils.Exceptions;

import static com.pacients.pacientservice.utils.Logs.ProgramLogs.CONFLIC_EXCEPTION;

public class ConflictException extends RuntimeException{
    public ConflictException(){ super(CONFLIC_EXCEPTION);}

    public ConflictException(String message){ super(message);}
}