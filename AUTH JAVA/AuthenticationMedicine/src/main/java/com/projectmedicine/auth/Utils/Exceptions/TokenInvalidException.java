/**************************************************************************

 File:        TokenExpiredException.java
 Copyright:   (c) 2023 NazImposter
 Description: Exception class for TokenExpiredException.
 Designed by: Larisa Pasa

 Module-History:
 Date        Author                Reason
 11.12.2023  Pasa Larisa      Structure
 **************************************************************************/
package com.projectmedicine.auth.Utils.Exceptions;


public class TokenInvalidException extends RuntimeException{
    public TokenInvalidException(String message) {
        super(message);
    }
}
