package com.projectmedicine.gateway.gateway.Utils.DTOTogRPC;

import com.projectmedicine.gateway.gateway.AuthenticationClient.protoComponents.Authentication;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.AuthenticationRequest;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.RegisterRequest;
import com.projectmedicine.gateway.gateway.Utils.Others.RoleEnum;

public class DtoToGrpc {

    public Authentication.AuthenticationRequestProto authenticationDtoToGrpc(AuthenticationRequest authenticationRequest){
        return Authentication.AuthenticationRequestProto.newBuilder()
                .setEmail(authenticationRequest.getEmail())
                .setPassword(authenticationRequest.getPassword())
                .build();
    }
    public Authentication.RegisterRequestProto registerDtoToGrpc(RegisterRequest registerRequest){
        return Authentication.RegisterRequestProto.newBuilder()
                .setEmail(registerRequest.getEmail())
                .setPassword(registerRequest.getPassword())
                .setRole(String.valueOf(registerRequest.getRole()))
                .build();
    }
    public Authentication.AccessTokenRequestProto tokenDtoToGrpc(String access_token){
        return Authentication.AccessTokenRequestProto.newBuilder()
                .setAccessToken(access_token)
                .build();
    }

    public Authentication.LogoutRequestProto logoutTokenDtoToGrpc(String access_token){
        return Authentication.LogoutRequestProto.newBuilder()
                .setAccessToken(access_token)
                .build();
    }

    public  Authentication.GetUserByIdRequestProto idDtoToGrpc(Integer userId){
        return Authentication.GetUserByIdRequestProto.newBuilder().setUserId(userId).build();
    }

    public  Authentication.GetUserByEmailRequestProto emailDtoToGrpc(String email){
        return Authentication.GetUserByEmailRequestProto.newBuilder().setEmail(email).build();
    }

    public RoleEnum roleGrpcToDto(Authentication.RoleEnumProto role) {
        switch (role) {
            case ADMIN:
                return RoleEnum.ADMIN;
            case PATIENT:
                return RoleEnum.PATIENT;
            case DOCTOR:
                return RoleEnum.DOCTOR;
            default:
                throw new IllegalArgumentException("Valoare necunoscută pentru RoleEnumProto: " + role);
        }
    }
}
