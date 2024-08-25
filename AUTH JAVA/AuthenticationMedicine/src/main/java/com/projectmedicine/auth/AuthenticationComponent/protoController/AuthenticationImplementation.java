package com.projectmedicine.auth.AuthenticationComponent.protoController;

import com.google.protobuf.Empty;
import com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication;
import com.projectmedicine.auth.AuthenticationComponent.protoComponents.AuthenticationServiceGrpc;

import com.projectmedicine.auth.AuthenticationComponent.service.Authentication.AuthenticationService;
import com.projectmedicine.auth.AuthenticationComponent.service.JWT.JWTServiceImplementation;
import com.projectmedicine.auth.AuthenticationComponent.service.Logout.*;
import com.projectmedicine.auth.UserComponent.protoComponents.User;
import com.projectmedicine.auth.UserComponent.service.UserServiceS;
import com.projectmedicine.auth.Utils.Exceptions.ConflictException;
import com.projectmedicine.auth.Utils.Exceptions.NotAcceptableException;
import com.projectmedicine.auth.Utils.Exceptions.*;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.csrf.CsrfException;

@Log4j2
@GrpcService
public class AuthenticationImplementation extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {
    private final AuthenticationService authenticationService;
    private final LogoutServiceImplementation logoutServiceImplementation;
    private final JWTServiceImplementation jwtServiceImplementation;
    private final UserServiceS userService;

    @Autowired
    public AuthenticationImplementation(AuthenticationService authenticationService, LogoutServiceImplementation logoutService, JWTServiceImplementation jwtServiceImplementation, UserServiceS userService) {
        this.authenticationService = authenticationService;
        this.logoutServiceImplementation = logoutService;
        this.jwtServiceImplementation = jwtServiceImplementation;
        this.userService=userService;


    }

    /**
     * Handles the registration request and sends a response to the client.
     *
     * @param request           The registration request.
     * @param responseObserver The response observer to send the response.
     */
    @Override
    public void register(Authentication.RegisterRequestProto request, StreamObserver<Authentication.RegisterResponseProto> responseObserver) {
        log.info("AuthenticationImplementation() => register => request {} ", request);
        log.info("A client try to register ... ");

        try {
            Authentication.AccessTokenResponseProto access_token = authenticationService.register(request);
            Authentication.RegisterResponseProto response = Authentication.RegisterResponseProto.newBuilder()
                    .setSuccessResponse(access_token)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (ConflictException e) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.CONFLICT.value())
                    .setErrorMessage(e.getMessage())
                    .build();
            Authentication.RegisterResponseProto response = Authentication.RegisterResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotAcceptableException e) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.NOT_ACCEPTABLE.value())
                    .setErrorMessage(e.getMessage())
                    .build();
            Authentication.RegisterResponseProto response = Authentication.RegisterResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (UnprocessableContentException e) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .setErrorMessage(e.getMessage())
                    .build();
            Authentication.RegisterResponseProto response = Authentication.RegisterResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (BadCredentialsException | DisabledException | AccountExpiredException | CredentialsExpiredException | ExpiredJwtException | TokenInvalidException e ) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.UNAUTHORIZED.value())
                    .setErrorMessage("Authentication failed: "+e.getMessage())
                    .build();
            Authentication.RegisterResponseProto response = Authentication.RegisterResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (AccessDeniedException e ) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.FORBIDDEN.value())
                    .setErrorMessage("Access denied: "+e.getMessage())
                    .build();
            Authentication.RegisterResponseProto response = Authentication.RegisterResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        catch (Exception e) {
            log.error("Unexpected error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setErrorMessage("Internal server error")
                    .build();
            Authentication.RegisterResponseProto response = Authentication.RegisterResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    /**
     * Handles the authentication request and sends a response to the client.
     *
     * @param request           The authentication request.
     * @param responseObserver The response observer to send the response.
     */
    @Override
    public void authenticate(Authentication.AuthenticationRequestProto request, StreamObserver<Authentication.AuthenticationResponseProto> responseObserver) {
        log.info("AuthenticationImplementation() => authenticate => request {} ", request);
        log.info("A client try to authenticate ... ");
        try {
            Authentication.AccessTokenResponseProto access_token = authenticationService.authenticate(request);
            Authentication.AuthenticationResponseProto response = Authentication.AuthenticationResponseProto.newBuilder()
                    .setSuccessResponse(access_token)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (ConflictException e) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.CONFLICT.value())
                    .setErrorMessage(e.getMessage())
                    .build();
            Authentication.AuthenticationResponseProto response = Authentication.AuthenticationResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotAcceptableException e) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.NOT_ACCEPTABLE.value())
                    .setErrorMessage(e.getMessage())
                    .build();
            Authentication.AuthenticationResponseProto response = Authentication.AuthenticationResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (UnprocessableContentException e) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .setErrorMessage(e.getMessage())
                    .build();
            Authentication.AuthenticationResponseProto response = Authentication.AuthenticationResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (BadCredentialsException | DisabledException | AccountExpiredException | CredentialsExpiredException | ExpiredJwtException | TokenInvalidException e ) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.UNAUTHORIZED.value())
                    .setErrorMessage("Authentication failed: "+e.getMessage())
                    .build();
            Authentication.AuthenticationResponseProto response = Authentication.AuthenticationResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (AccessDeniedException e ) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.FORBIDDEN.value())
                    .setErrorMessage("Access denied: "+e.getMessage())
                    .build();
            Authentication.AuthenticationResponseProto response = Authentication.AuthenticationResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            log.error("Unexpected error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setErrorMessage("Internal server error")
                    .build();
            Authentication.AuthenticationResponseProto response = Authentication.AuthenticationResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    /**
     * Handles user logout based on the provided logout request.
     *
     * @param request           The logout request payload.
     * @param responseObserver The response observer to send the response.
     */
    @Override
    public void logout(Authentication.LogoutRequestProto request, StreamObserver<Authentication.LogoutResponseProto> responseObserver) {

        try {
            String jwt = request.getAccessToken();
            Authentication.LogoutSuccessfulResponseProto logoutSuccessful = logoutServiceImplementation.logout(jwt);
            Authentication.LogoutResponseProto response = Authentication.LogoutResponseProto.newBuilder()
                    .setSuccessResponse(logoutSuccessful)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotAcceptableException e) {
            log.error("Error during logout-> token seems invalid: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.NOT_ACCEPTABLE.value())
                    .setErrorMessage(e.getMessage())
                    .build();
            Authentication.LogoutResponseProto response = Authentication.LogoutResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotFoundException e) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.NOT_FOUND.value())
                    .setErrorMessage(e.getMessage())
                    .build();
            Authentication.LogoutResponseProto response = Authentication.LogoutResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (BadCredentialsException | DisabledException | AccountExpiredException | CredentialsExpiredException | ExpiredJwtException | TokenInvalidException e ) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.UNAUTHORIZED.value())
                    .setErrorMessage("Authentication failed: "+e.getMessage())
                    .build();
            Authentication.LogoutResponseProto response = Authentication.LogoutResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (AccessDeniedException e ) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.FORBIDDEN.value())
                    .setErrorMessage("Access denied: "+e.getMessage())
                    .build();
            Authentication.LogoutResponseProto response = Authentication.LogoutResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            log.error("Unexpected error during logout: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setErrorMessage("Internal server error")
                    .build();
            Authentication.LogoutResponseProto response = Authentication.LogoutResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }


    /**
     * Checks the validity of the provided access token.
     *
     * @param request           The access token request payload.
     * @param responseObserver The response observer to send the response.
     */
    @Override
    public void isTokenValid(Authentication.AccessTokenRequestProto request, StreamObserver<Authentication.TokenValidityResponseProto> responseObserver) {
        String jwt = request.getAccessToken();
       try {
            Authentication.isValidResponseProto isValid = authenticationService.getValidityToken(jwt);
            Authentication.TokenValidityResponseProto response = Authentication.TokenValidityResponseProto.newBuilder()
                    .setSuccessResponse(isValid)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (ExpiredJwtException e) {
            log.error("Token expired: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.UNAUTHORIZED.value())
                    .setErrorMessage("Token expired: " + e.getMessage())
                    .build();
            Authentication.TokenValidityResponseProto response = Authentication.TokenValidityResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
       catch (SignatureException e) {
           log.error("Token singature incorrect : {}", e.getMessage());
           Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                   .setErrorCode(HttpStatus.UNAUTHORIZED.value())
                   .setErrorMessage("Token signature incorrect: " + e.getMessage())
                   .build();
           Authentication.TokenValidityResponseProto response = Authentication.TokenValidityResponseProto.newBuilder()
                   .setErrorResponse(errorResponse)
                   .build();
           responseObserver.onNext(response);
           responseObserver.onCompleted();
       }
       /* just for now to catch any error I forgot*/
        catch (Exception e) {
            log.error("Unexpected error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setErrorMessage("Internal server error")
                    .build();
            Authentication.TokenValidityResponseProto response = Authentication.TokenValidityResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }


    }

    /**
     * Checks the validity of the provided access token by considering expiration or revocation.
     *
     * @param request           The access token request payload.
     * @param responseObserver The response observer to send the response.
     */
    @Override
    public void isTokenValidByExpiredOrRevoked(Authentication.AccessTokenRequestProto request, StreamObserver<Authentication.TokenValidityResponseProto> responseObserver) {
        String jwt = request.getAccessToken();
        try {
            Authentication.isValidResponseProto isValid = authenticationService.getValidityTokenByExpiredOrRevoked(jwt);
            Authentication.TokenValidityResponseProto response = Authentication.TokenValidityResponseProto.newBuilder()
                    .setSuccessResponse(isValid)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (ExpiredJwtException e) {
            log.error("Token expired: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.UNAUTHORIZED.value())
                    .setErrorMessage("Token expired: " + e.getMessage())
                    .build();
            Authentication.TokenValidityResponseProto response = Authentication.TokenValidityResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (SignatureException e) {
            log.error("Token singature incorrect : {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.UNAUTHORIZED.value())
                    .setErrorMessage("Token signature incorrect: " + e.getMessage())
                    .build();
            Authentication.TokenValidityResponseProto response = Authentication.TokenValidityResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        /* just for now to catch any error I forgot*/
        catch (Exception e) {
            log.error("Unexpected error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setErrorMessage("Internal server error")
                    .build();
            Authentication.TokenValidityResponseProto response = Authentication.TokenValidityResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }


    /**
     * Retrieves user information based on the provided user ID.
     *
     * @param request           The request containing the user ID.
     * @param responseObserver The response observer to send the response.
     */
    @Override
    public void getUserById(Authentication.GetUserByIdRequestProto request, StreamObserver<Authentication.UserResponseProto> responseObserver) {
        log.info("UserImplementation() => getUserById => request {} ", request);
        log.info("Trying to get a user by id ... ");

        try {
            Authentication.UserBodyProto userBody = userService.getuserById(request);
            Authentication.UserResponseProto response = Authentication.UserResponseProto.newBuilder()
                    .setUserResponse(userBody)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotFoundException e) {
            log.error("Error during registration: {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.NOT_FOUND.value())
                    .setErrorMessage(e.getMessage())
                    .build();
            Authentication.UserResponseProto response = Authentication.UserResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    /**
     * Retrieves user information based on the provided email address.
     *
     * @param request           The request containing the user's email address.
     * @param responseObserver The response observer to send the response.
     */
    @Override
    public void getUserByEmail(Authentication.GetUserByEmailRequestProto request, StreamObserver<Authentication.UserResponseProto> responseObserver) {
        log.info("UserImplementation() => getUserById => request {} ", request);
        log.info("Trying to get a user by id ... ");

        try {
            Authentication.UserBodyProto userBody = userService.getUserByEmail(request);
            Authentication.UserResponseProto response = Authentication.UserResponseProto.newBuilder()
                    .setUserResponse(userBody)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotFoundException e) {
            log.error("Error during getUserByEmail(): {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.NOT_FOUND.value())
                    .setErrorMessage(e.getMessage())
                    .build();
            Authentication.UserResponseProto response = Authentication.UserResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (UnprocessableContentException e) {
            log.error("Error during getUserByEmail(): {}", e.getMessage());
            Authentication.ErrorResponseProto errorResponse = Authentication.ErrorResponseProto.newBuilder()
                    .setErrorCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .setErrorMessage(e.getMessage())
                    .build();
            Authentication.UserResponseProto response = Authentication.UserResponseProto.newBuilder()
                    .setErrorResponse(errorResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    /**
     * Deletes a user based on the provided delete user request.
     *
     * @param request           The delete user request payload.
     * @param responseObserver The response observer to send the response.
     */
    @Override
    public void deleteUser(Authentication.DeleteUserRequestProto request, StreamObserver<Authentication.DeleteUserResponseProto> responseObserver) {
        super.deleteUser(request, responseObserver);
    }


}
