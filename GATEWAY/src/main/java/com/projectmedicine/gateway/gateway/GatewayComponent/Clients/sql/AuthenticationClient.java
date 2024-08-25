package com.projectmedicine.gateway.gateway.GatewayComponent.Clients.sql;

import com.projectmedicine.gateway.gateway.AuthenticationClient.protoComponents.Authentication;
import com.projectmedicine.gateway.gateway.AuthenticationClient.protoComponents.AuthenticationServiceGrpc;
import com.projectmedicine.gateway.gateway.Utils.DTOTogRPC.DtoToGrpc;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.AuthenticationRequest;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.RegisterRequest;
import io.grpc.ManagedChannel;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Component
public class AuthenticationClient {
    private final ManagedChannel grpcChannel;
    AuthenticationServiceGrpc.AuthenticationServiceBlockingStub clientStub ;
    DtoToGrpc dtoToGrpc = new DtoToGrpc();
    public AuthenticationClient(ManagedChannel grpcChannel) {
        this.grpcChannel = grpcChannel;
        this.clientStub = AuthenticationServiceGrpc.newBlockingStub(this.grpcChannel);
    }
    public ResponseEntity<?> register(RegisterRequest registerRequest){
        Authentication.RegisterResponseProto responseProto = clientStub.register(dtoToGrpc.registerDtoToGrpc(registerRequest));
        if (responseProto.hasSuccessResponse()) {
            Authentication.AccessTokenResponseProto successResponse = responseProto.getSuccessResponse();
            String access_token = successResponse.getAccessToken();
            System.out.println("Success register! Access Token: " + successResponse.getAccessToken());
            return new ResponseEntity<>(access_token, HttpStatus.CREATED);

        } else  {
            Authentication.ErrorResponseProto errorResponse = responseProto.getErrorResponse();
            System.out.println("Error Code: " + errorResponse.getErrorCode());
            System.out.println("Error Message: " + errorResponse.getErrorMessage());
            return new ResponseEntity<>(errorResponse.getErrorMessage(), HttpStatus.valueOf(errorResponse.getErrorCode()));
        }

    }
    public ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest) {
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
    public ResponseEntity<?> getUserById(Integer userId){

        Authentication.UserResponseProto responseProto = clientStub.getUserById(dtoToGrpc.idDtoToGrpc(userId));
        if (responseProto.hasUserResponse()) {
            Authentication.UserBodyProto successResponse = responseProto.getUserResponse();
            System.out.println("Success getUserById! {} " + successResponse);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } else  {
            Authentication.ErrorResponseProto errorResponse = responseProto.getErrorResponse();
            System.out.println("Error Code: " + errorResponse.getErrorCode());
            System.out.println("Error Message: " + errorResponse.getErrorMessage());
            return new ResponseEntity<>(errorResponse.getErrorMessage(), HttpStatus.valueOf(errorResponse.getErrorCode()));
        }
    }

    public ResponseEntity<?> getUserByEmail(String email){

        Authentication.UserResponseProto responseProto = clientStub.getUserByEmail(dtoToGrpc.emailDtoToGrpc(email));
        if (responseProto.hasUserResponse()) {
            Authentication.UserBodyProto successResponse = responseProto.getUserResponse();
            System.out.println("Success getUserByEmail! {} " + successResponse);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } else  {
            Authentication.ErrorResponseProto errorResponse = responseProto.getErrorResponse();
            System.out.println("Error Code: " + errorResponse.getErrorCode());
            System.out.println("Error Message: " + errorResponse.getErrorMessage());
            return new ResponseEntity<>(errorResponse.getErrorMessage(), HttpStatus.valueOf(errorResponse.getErrorCode()));
        }
    }
    public ResponseEntity<?> isTokenValid(String jwt){

        Authentication.TokenValidityResponseProto responseProto = clientStub.isTokenValid(dtoToGrpc.tokenDtoToGrpc(jwt));
        if (responseProto.hasSuccessResponse()) {
            Authentication.isValidResponseProto successResponse = responseProto.getSuccessResponse();
            System.out.println("Success isTokenValid! {} " + successResponse);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } else  {
            Authentication.ErrorResponseProto errorResponse = responseProto.getErrorResponse();
            System.out.println("Error Code: " + errorResponse.getErrorCode());
            System.out.println("Error Message: " + errorResponse.getErrorMessage());
            return new ResponseEntity<>(errorResponse.getErrorMessage(), HttpStatus.valueOf(errorResponse.getErrorCode()));
        }
    }
    public ResponseEntity<?> isTokenValidByExpiredOrRevoked(String jwt){

        Authentication.TokenValidityResponseProto responseProto = clientStub.isTokenValidByExpiredOrRevoked(dtoToGrpc.tokenDtoToGrpc(jwt));
        if (responseProto.hasSuccessResponse()) {
            Authentication.isValidResponseProto successResponse = responseProto.getSuccessResponse();
            System.out.println("Success isTokenValidByExpiredOrRevoked! {} " + successResponse);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } else  {
            Authentication.ErrorResponseProto errorResponse = responseProto.getErrorResponse();
            System.out.println("Error Code: " + errorResponse.getErrorCode());
            System.out.println("Error Message: " + errorResponse.getErrorMessage());
            return new ResponseEntity<>(errorResponse.getErrorMessage(), HttpStatus.valueOf(errorResponse.getErrorCode()));
        }
    }


    public ResponseEntity<?> logout(String jwt){

        log.info("A client is logging out with jwt {} ...", jwt);

        Authentication.LogoutResponseProto responseProto = clientStub.logout(dtoToGrpc.logoutTokenDtoToGrpc(jwt));
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
