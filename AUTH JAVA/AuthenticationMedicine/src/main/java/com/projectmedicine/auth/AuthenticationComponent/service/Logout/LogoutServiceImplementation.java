package com.projectmedicine.auth.AuthenticationComponent.service.Logout;


import com.projectmedicine.auth.UserComponent.model.repository.TokenRepository;
import com.projectmedicine.auth.Utils.Exceptions.NotAcceptableException;
import com.projectmedicine.auth.Utils.Exceptions.NotFoundException;
import com.projectmedicine.auth.Utils.Exceptions.TokenInvalidException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.projectmedicine.auth.Utils.Others.Logs.EMPTY_INVALID_EXCEPTION_TOKEN;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutServiceImplementation implements  LogoutService{
    private final TokenRepository tokenRepository;
    @Override
    public Authentication.LogoutSuccessfulResponseProto logout(String jwt) {
        log.info("TOKEN  AICI {}", jwt);
        try {
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                log.info("Invalid JWT format: {}", jwt);
                throw new TokenInvalidException(EMPTY_INVALID_EXCEPTION_TOKEN);
            }

            log.info("jwt inainte de scos bearer {}", jwt);
            jwt = jwt.substring(7); // Remove "Bearer " prefix
            log.info("jwt {}", jwt);

            var storedToken = tokenRepository.findByToken(jwt).orElse(null);
            log.info("storedToken {}", storedToken);

            if (storedToken != null) {
                storedToken.setExpired(true);
                storedToken.setRevoked(true);
                tokenRepository.save(storedToken);
                SecurityContextHolder.clearContext();
                return Authentication.LogoutSuccessfulResponseProto.newBuilder().setIsLoggedOut(true).build();
            } else {
                throw new NotFoundException(EMPTY_INVALID_EXCEPTION_TOKEN);

            }
        }   catch (SignatureException e) {
            throw new SignatureException(e.getMessage());
        }

    }

}
