package com.projectmedicine.auth.AuthenticationComponent.service.Authentication;


import com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication;

import com.projectmedicine.auth.AuthenticationComponent.service.JWT.JWTServiceImplementation;
import com.projectmedicine.auth.UserComponent.model.database.idm.TokenTable;
import com.projectmedicine.auth.UserComponent.model.database.idm.UserTable;
import com.projectmedicine.auth.UserComponent.model.database.idm.enums.RoleEnum;
import com.projectmedicine.auth.UserComponent.model.database.idm.enums.TokenType;
import com.projectmedicine.auth.UserComponent.model.repository.*;

import com.projectmedicine.auth.Utils.Exceptions.*;
import com.projectmedicine.auth.Utils.Validators.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import static com.projectmedicine.auth.Utils.Others.Logs.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    /*------------------------------------------  VARIABLES ----------------------------------------------------------*/
    @Autowired
    private final UserRepository usersRepository;
    @Autowired
    private  final PasswordEncoder passwordEncoder;
    @Autowired
    private final JWTServiceImplementation jwtService;
    @Autowired
    private final TokenRepository tokenRepository;
    @Autowired
    private final AuthenticationManager authenticationManager;

    private final StringValidator stringValidator=new StringValidator();
    private final EmailValidator emailValidator=new EmailValidator();
    private final PasswordValidator passwordValidator=new PasswordValidator();


    /*------------------------------------------  REGISTER -----------------------------------------------------------*/

    /**
     * Registers a new user based on the provided registration request.
     *
     * @param registerRequest The registration request payload.
     * @return AuthenticationResponse containing access and refresh tokens.
     */
    public Authentication.AccessTokenResponseProto register(Authentication.RegisterRequestProto registerRequest)
    {
        log.info("RegisterRequest => register{} {} {} {}"
                ,registerRequest.getEmail(), registerRequest.getPassword(), registerRequest.getRole(), usersRepository.findByEmail(registerRequest.getEmail()).isPresent());
        if(usersRepository.findByEmail(registerRequest.getEmail()).isPresent())
        {
            log.info("already exist user with this email");
            throw new ConflictException(CONFLIC_EXCEPTION_ALREADY_REGISTERED);
        }
        log.info("after checking user already existene");


        if(null == registerRequest.getEmail() || null == registerRequest.getPassword()   ){
            throw new NotAcceptableException(NOT_ACCEPTABLE_EXCEPTION);
        }

        log.info("Check fields validity: {} {}", emailValidator.isValid(registerRequest.getEmail(),null), passwordValidator.isValid(registerRequest.getPassword(), null) );
        if(!emailValidator.isValid(registerRequest.getEmail(), null)
                || !passwordValidator.isValid(registerRequest.getPassword(), null)

        ){
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS);
        }
        log.info("fields are valid");

        log.info("try build user now ");
        var user = UserTable.builder()
                .email(registerRequest.getEmail())
                .hashedPassword(passwordEncoder.encode(registerRequest.getPassword()))
                .roleEnum(RoleEnum.valueOf(registerRequest.getRole()))
                .build();
        log.info("i have builded user: {} ", user);
        var savedUser = usersRepository.save(user);
        log.info("i have here saved user {}", savedUser);
        var jwtToken = jwtService.generateToken(user);
        log.info("REGISTER SAVE ACCESS_TOKEN {}", jwtToken);

        saveUserToken(savedUser, jwtToken);
        log.info("Saving user {} ...  " , user);

        /* return creating a response token with specific constructor proto */
       return  Authentication.AccessTokenResponseProto.newBuilder()
                .setAccessToken(jwtToken)
                .build();
    }


    /*------------------------------------------  AUTHENTICATE -----------------------------------------------------------*/
    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param request The authentication request payload.
     * @return AuthenticationResponse containing access and refresh tokens.
     */
    public Authentication.AccessTokenResponseProto authenticate(Authentication.AuthenticationRequestProto  request) {
        /* IF IS NOT REGISTERED*/
        if( ! usersRepository.findByEmail(request.getEmail()).isPresent())
        {
            throw new ConflictException(CONFLIC_EXCEPTION_NOT_REGISTERED);
        }

        log.info("AuthenticationService => authenticate{}",request);
        if(null == request.getEmail() || null == request.getPassword()){
            throw new NotAcceptableException(NOT_ACCEPTABLE_EXCEPTION);
        }

        if(!emailValidator.isValid(request.getEmail(), null)  || !passwordValidator.isValid(request.getPassword(), null)){
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS);
        }


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = usersRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        /* return creating a response token with specific constructor proto */
        return  Authentication.AccessTokenResponseProto.newBuilder()
                .setAccessToken(jwtToken)
                .build();
    }

    /*------------------------------------------  SAVE TOKENS -----------------------------------------------------------*/
    /**
     * Saves a user's token in the repository.
     *
     * @param userTable     The user for whom the token is generated.
     * @param jwtToken The JWT token to be saved.
     */
    private void saveUserToken(UserTable userTable, String jwtToken) {

        var token = TokenTable.builder()
                .userTable(userTable)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /*------------------------------------------ REVOKE TOKENS -----------------------------------------------------------*/
    /**
     * Revokes all valid tokens for a given user.
     *
     * @param userTable The user for whom tokens need to be revoked.
     */
    private void revokeAllUserTokens(UserTable userTable) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(userTable.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public Authentication.isValidResponseProto getValidityToken(String token) {
        final String userEmail = jwtService.extractUserEmail(token);
        if (userEmail != null) {
            var user = this.usersRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(token, user)) {
                /* return creating a response token with specific constructor proto */
                return  Authentication.isValidResponseProto.newBuilder()
                        .setIsValid(true)
                        .build();
            } else {

                return  Authentication.isValidResponseProto.newBuilder()
                        .setIsValid(false)
                        .build();
            }
        }
        else{
            return  Authentication.isValidResponseProto.newBuilder()
                    .setIsValid(false)
                    .build();
        }
    }
    public Authentication.isValidResponseProto getValidityTokenByExpiredOrRevoked(String token){
        boolean response = tokenRepository.findByToken(token)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);

        return Authentication.isValidResponseProto.newBuilder()
                .setIsValid(response)
                .build();

    }
}
