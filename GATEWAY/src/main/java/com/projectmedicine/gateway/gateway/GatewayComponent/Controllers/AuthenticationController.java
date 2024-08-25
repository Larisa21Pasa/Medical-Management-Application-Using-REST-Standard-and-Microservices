package com.projectmedicine.gateway.gateway.GatewayComponent.Controllers;

import com.google.protobuf.Empty;
import com.projectmedicine.gateway.gateway.AuthenticationClient.protoComponents.Authentication;
import com.projectmedicine.gateway.gateway.AuthenticationClient.protoComponents.AuthenticationServiceGrpc;
import com.projectmedicine.gateway.gateway.Utils.DTOTogRPC.DtoToGrpc;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.AuthenticationRequest;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.RegisterRequest;
import io.grpc.ManagedChannel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/medical_office/gateway/auth")
public class AuthenticationController {
    /* Class reated just for test separate grpc*/
    private final ManagedChannel grpcChannel;
    AuthenticationServiceGrpc.AuthenticationServiceBlockingStub clientStub ;
    DtoToGrpc dtoToGrpc = new DtoToGrpc();
    public AuthenticationController(ManagedChannel grpcChannel) {
        this.grpcChannel = grpcChannel;
        this.clientStub = AuthenticationServiceGrpc.newBlockingStub(this.grpcChannel);
    }

    /**
     * Handles user registration requests.
     *
     * @param registerRequest The registration request payload.
     * @return ResponseEntity containing the authentication response.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        log.info("A client try to register ... " );
        Authentication.RegisterResponseProto responseProto = clientStub.register(dtoToGrpc.registerDtoToGrpc(registerRequest));
        if (responseProto.hasSuccessResponse()) {
            Authentication.AccessTokenResponseProto successResponse = responseProto.getSuccessResponse();
            String access_token = successResponse.getAccessToken();
            System.out.println("Success register! Access Token: " + successResponse.getAccessToken());
            return new ResponseEntity<>(access_token, HttpStatus.OK);

        } else  {
            Authentication.ErrorResponseProto errorResponse = responseProto.getErrorResponse();
            System.out.println("Error Code: " + errorResponse.getErrorCode());
            System.out.println("Error Message: " + errorResponse.getErrorMessage());
            return new ResponseEntity<>(errorResponse.getErrorMessage(), HttpStatus.valueOf(errorResponse.getErrorCode()));
        }
    }

    /**
     * Handles user authentication requests.
     *
     * @param authenticationRequest The authentication request payload.
     * @return ResponseEntity containing the authentication response.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        log.info("A client try to authenticate  ... ");


        Authentication.AuthenticationResponseProto responseProto = clientStub.authenticate(dtoToGrpc.authenticationDtoToGrpc(authenticationRequest));
        if (responseProto.hasSuccessResponse()) {
            Authentication.AccessTokenResponseProto successResponse = responseProto.getSuccessResponse();
            String access_token = successResponse.getAccessToken();
            System.out.println("Success auth! Access Token: " + successResponse.getAccessToken());
            return new ResponseEntity<>(access_token, HttpStatus.OK);

        } else  {
            Authentication.ErrorResponseProto errorResponse = responseProto.getErrorResponse();
            System.out.println("Error Code: " + errorResponse.getErrorCode());
            System.out.println("Error Message: " + errorResponse.getErrorMessage());
            return new ResponseEntity<>(errorResponse.getErrorMessage(), HttpStatus.valueOf(errorResponse.getErrorCode()));
        }

    }



    @GetMapping("/token/{tokenString}")
    public ResponseEntity<?> getValidityToken(
            @PathVariable String tokenString
    )
    {
        log.info("A client try to check token validity ... ");
        Authentication.TokenValidityResponseProto responseProto = clientStub.isTokenValid(dtoToGrpc.tokenDtoToGrpc(tokenString));
        if (responseProto.hasSuccessResponse()) {
            Authentication.isValidResponseProto successResponse = responseProto.getSuccessResponse();
            Boolean isValid = successResponse.getIsValid();
            return new ResponseEntity<>(isValid, HttpStatus.OK);

        } else  {
            Authentication.ErrorResponseProto errorResponse = responseProto.getErrorResponse();
            System.out.println("Error Code: " + errorResponse.getErrorCode());
            System.out.println("Error Message: " + errorResponse.getErrorMessage());
            return new ResponseEntity<>(errorResponse.getErrorMessage(), HttpStatus.valueOf(errorResponse.getErrorCode()));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        log.info("A client is logging out...");

        Authentication.LogoutResponseProto responseProto = clientStub.logout(dtoToGrpc.logoutTokenDtoToGrpc(authorizationHeader));
        if (responseProto.hasSuccessResponse()) {
            Authentication.LogoutSuccessfulResponseProto successResponse = responseProto.getSuccessResponse();
            Boolean isLoggedOut = successResponse.getIsLoggedOut();
            return new ResponseEntity<>(isLoggedOut, HttpStatus.OK);

        } else  {
            Authentication.ErrorResponseProto errorResponse = responseProto.getErrorResponse();
            System.out.println("Error Code: " + errorResponse.getErrorCode());
            System.out.println("Error Message: " + errorResponse.getErrorMessage());
            return new ResponseEntity<>(errorResponse.getErrorMessage(), HttpStatus.valueOf(errorResponse.getErrorCode()));
        }
    }

}
