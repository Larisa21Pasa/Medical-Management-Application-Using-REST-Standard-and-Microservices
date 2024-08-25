package com.example.authenticationclient;

import com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient;
import com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.AuthenticationServiceGrpc;

import com.example.authenticationclient.UserComponent.protoComponents.User;
import com.example.authenticationclient.UserComponent.protoComponents.UserServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.StatusRuntimeException;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


@Log4j2
@SpringBootApplication
public class AuthenticationClientApplication {
    /* DUMMY CODE TO CHECK AUTHENTICATION METHODS BEFORE INTEGRATING INTO PROJECT MEDICINE CODE */
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8083).usePlaintext().build();
        AuthenticationServiceGrpc.AuthenticationServiceBlockingStub clientStub = AuthenticationServiceGrpc.newBlockingStub(channel);

        Authenticationclient.AuthenticationRequestProto correct = Authenticationclient.AuthenticationRequestProto.newBuilder()
                .setEmail("uiui@example.com")
                .setPassword("n")
                .build();
        try {
            Authenticationclient.AuthenticationResponseProto response = clientStub.authenticate(correct);
            if (response.hasSuccessResponse()) {
                Authenticationclient.AccessTokenResponseProto successResponseAuth = response.getSuccessResponse();
                System.out.println("Success! Access Token: " + successResponseAuth.getAccessToken());
            } else if (response.hasErrorResponse()) {
                Authenticationclient.ErrorResponseProto errorResponse = response.getErrorResponse();
                System.out.println("Error Code: " + errorResponse.getErrorCode());
                System.out.println("Error Message: " + errorResponse.getErrorMessage());
            } else {
                System.out.println("Unknown response type.");
            }
        } catch (StatusRuntimeException e) {
            System.out.println("Error during registration: " + e.getStatus().getDescription());
        }

        SpringApplication.run(AuthenticationClientApplication.class, args);
    }
}



