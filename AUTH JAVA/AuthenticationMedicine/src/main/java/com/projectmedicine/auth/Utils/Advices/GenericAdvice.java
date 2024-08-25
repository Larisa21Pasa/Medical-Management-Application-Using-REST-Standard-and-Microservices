/**************************************************************************

 File:        Exception.java
 Copyright:   (c) 2023 NazImposter
 Description:
 Designed by: Matei Rares

 Module-History:
 Date        Author                Reason
 10.12.2023  Larisa Pasa           Added handling for various exception for 401 & 403.
 29.11.2023  Matei Rares           Initial creation for managing Exception.

 **************************************************************************/

package com.projectmedicine.auth.Utils.Advices;

import com.projectmedicine.auth.Utils.Exceptions.TokenInvalidException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.MethodNotAllowedException;

@ControllerAdvice
public class GenericAdvice {
    /*------------ 401 - Authentication exception -> Server does not know who you are -------------*/
    @ResponseBody
    @ExceptionHandler({BadCredentialsException.class, DisabledException.class, AccountExpiredException.class,  CredentialsExpiredException.class, TokenInvalidException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>("Authentication failed: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler({ExpiredJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>("Token expired: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /*------------ 403 - Authorization exception -> Server does know who you are, but you not have access to this resource (patient who want to create a cosnultation, for example) -------------*/
    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>("Access denied: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CsrfException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleCsrfException(CsrfException ex) {
        return new ResponseEntity<>("CSRF validation failed: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    /*------------ 405 - Just in case I use wrong some method and don't know from where I get error -------------*/
    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<?> handleMethodNotAllowedException(MethodNotAllowedException ex) {
        return new ResponseEntity<>("Method not allowed: " + ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

}
