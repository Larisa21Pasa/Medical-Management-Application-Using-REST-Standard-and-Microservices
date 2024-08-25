package com.projectmedicine.auth.Utils.Exceptions;


import static com.projectmedicine.auth.Utils.Others.Logs.CONFLIC_EXCEPTION;

public class ConflictException extends RuntimeException{
    public ConflictException(){ super(CONFLIC_EXCEPTION);}

    public ConflictException(String message){ super(message);}
}