package com.projectmedicine.auth.UserComponent.service;

import com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication;
import com.projectmedicine.auth.UserComponent.model.database.idm.UserTable;

import com.projectmedicine.auth.UserComponent.model.repository.UserRepository;
import com.projectmedicine.auth.UserComponent.protoComponents.User;
import com.projectmedicine.auth.Utils.Exceptions.NotFoundException;
import com.projectmedicine.auth.Utils.Validators.EmailValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.projectmedicine.auth.Utils.Others.Logs.NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
//@Transactional
@Log4j2
public class UserServiceS {
    private final UserRepository userRepository;
    private final EmailValidator emailValidator=new EmailValidator();

    public Authentication.UserBodyProto getuserById(Authentication.GetUserByIdRequestProto UserId) {
        log.info("Fetching user by id {} .", UserId);
        UserTable userTable = userRepository.findByUserId(UserId.getUserId());
        log.info("usertable {} ", userTable);
        if( null == userTable)
        {
            throw  new NotFoundException(NOT_FOUND_EXCEPTION);
        }

        return
                Authentication.UserBodyProto.newBuilder()
                        .setUserId(userTable.getUserId())
                        .setEmail(userTable.getEmail())
                        .setHashedPassword(userTable.getHashedPassword())
                        .setRoleEnumValue(userTable.getRoleEnum().ordinal())
                        .addAllTokens(userTable.getTokens()
                                .stream()
                                .map(token -> Authentication.TokenProto.newBuilder()
                                        .setId(token.getId())
                                        .setToken(token.getToken())
                                        .setTokenType(Authentication.TokenTypeProto.forNumber(token.getTokenType().ordinal()))
                                        .setRevoked(token.isRevoked())
                                        .setExpired(token.isExpired())
                                        .build())
                                .collect(Collectors.toList())
                        )

                .build();

    }
    public Authentication.UserBodyProto getUserByEmail(Authentication.GetUserByEmailRequestProto email) {
        //TODO ADAUGA VALIDARE PE EMAIL SI AICI
        log.info("Fetching user by email {} .", email);
        UserTable userTable = userRepository.findUserByEmail(email.getEmail());
        log.info("usertable {} ", userTable);
        if( null == userTable)
        {
            throw  new NotFoundException(NOT_FOUND_EXCEPTION);
        }

        return
                Authentication.UserBodyProto.newBuilder()
                        .setUserId(userTable.getUserId())
                        .setEmail(userTable.getEmail())
                        .setHashedPassword(userTable.getHashedPassword())
                        .setRoleEnum(Authentication.RoleEnumProto.valueOf(userTable.getRoleEnum().name()))
                        .addAllTokens(userTable.getTokens()
                                .stream()
                                .map(token -> Authentication.TokenProto.newBuilder()
                                        .setId(token.getId())
                                        .setToken(token.getToken())
                                        .setTokenType(Authentication.TokenTypeProto.forNumber(token.getTokenType().ordinal()))
                                        .setRevoked(token.isRevoked())
                                        .setExpired(token.isExpired())
                                        .build())
                                .collect(Collectors.toList())
                        )

                        .build();
    }

    public UserTable getUserRoleByEmail(String email) {
        log.info("Fetching role by email {} .", email);
        return userRepository.findUserByEmail(email);
    }
}
