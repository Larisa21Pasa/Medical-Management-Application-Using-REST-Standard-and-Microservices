package com.projectmedicine.gateway.gateway.Utils.Exceptions;


import static com.projectmedicine.gateway.gateway.Utils.Logs.ConsultationsLog.CONFLICT_MESSAGE;

public class ConflictException extends RuntimeException{
    public ConflictException(){ super(CONFLICT_MESSAGE);}

    public ConflictException(String message){ super(message);}
}