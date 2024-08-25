package com.projectmedicine.gateway.gateway.Utils.Exceptions;

public class TokenInvalidException extends RuntimeException{
    public TokenInvalidException(String message) {
        super(message);
    }
}
