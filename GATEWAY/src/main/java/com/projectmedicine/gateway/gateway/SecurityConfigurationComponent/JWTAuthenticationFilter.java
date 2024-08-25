package com.projectmedicine.gateway.gateway.SecurityConfigurationComponent;


import com.projectmedicine.gateway.gateway.GatewayComponent.Clients.sql.AuthenticationClient;
import com.projectmedicine.gateway.gateway.Utils.DTOTogRPC.DtoToGrpc;
import com.projectmedicine.gateway.gateway.Utils.Exceptions.NotAcceptableException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.projectmedicine.gateway.gateway.Utils.Logs.ConsultationsLog.NOT_ACCEPTABLE_EXCEPTION;

@Log4j2
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter
{
    @Autowired
    private final JWTServiceImplementation jwtService;
    @Autowired
    private  final UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationClient authenticationClient;
    DtoToGrpc dtoToGrpc = new DtoToGrpc();

//    @Autowired
//    private final TokenRepository tokenRepository;

    /**
     * Filters incoming requests and performs token-based authentication.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain for handling the request.
     * @throws ServletException If an exception occurs during the filtering process.
     * @throws IOException      If an I/O exception occurs.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, /* Extract new data from the incoming request. */
            @NonNull HttpServletResponse response, /* Create a response based on the request and applied filters. */
            @NonNull FilterChain filterChain /* Design Pattern that contains a list of other filters to be applied. */
    ) throws ServletException, IOException {
        log.info("Processing request in JWTAuthenticationFilter...");
        if (
                request.getServletPath().contains("/api/medical_office/gateway/patients/register/")
                || request.getServletPath().contains("/api/medical_office/gateway/patients/authenticate")
                || request.getServletPath().contains("/api/medical_office/gateway/doctors/register")
                || request.getServletPath().contains("/api/medical_office/gateway/doctors/authenticate")
                || request.getServletPath().contains("/api/medical_office/gateway/logout")

        ) {
            log.info("Request {} is related to authentication, redirecting to auth endpoint.", request);
            filterChain.doFilter(request, response);
            return;
        }
        final String authenticationHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        /* Check JWT token */

        if ((authenticationHeader != null) && (authenticationHeader.startsWith("Bearer "))) {
            /* Extract token from authentication/authorization header */
            jwt = authenticationHeader.substring(7);

            /* Extract user detail from token */
            userEmail = jwtService.extractUserEmail(jwt);

        /*
           Check if the userEmail is successfully extracted from the JWT
           and verify that the user is not already authenticated.
        */

            if (userEmail == null) {
                throw new NotAcceptableException(NOT_ACCEPTABLE_EXCEPTION);

            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                log.info("User details extracted from JWT. Checking token validity.");
            /*
                Get the current user's email by overriding the loadUserByUsername method
                in the AccessManagerConfiguration class. This method is invoked by the
                authentication provider to load user details based on the provided username,
                which, in this case, represents the user's email.
            */
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                log.info("userDetails = {}", userDetails);
            /*
             Check if the retrieved token from the repository is not null,
             then validate if the token is not expired and not revoked.
              If the token is valid, isTokenValid is set to true, otherwise, it's set to false.
             */
//                var isTokenValid = tokenRepository.findByToken(jwt)
//                        .map(t -> !t.isExpired() && !t.isRevoked())
//                        .orElse(false);

                boolean isTokenValidByExpiredOrRevoked = authenticationClient.isTokenValidByExpiredOrRevoked(jwt).hasBody(); //equivalent to method above
                if (jwtService.isTokenValid(jwt, userDetails) && isTokenValidByExpiredOrRevoked) {
                    log.info("JWT is valid.");
                /*
                    If the token is valid, it is necessary to update the SecurityContextHolder
                    and forward the request to the DispatcherServlet. This ensures that the user
                    is properly authenticated and the request continues through the filter chain.
                */
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }


            }
            filterChain.doFilter(request, response);

        } else {
            log.info("No valid JWT found, continuing with the filter chain.");
            filterChain.doFilter(request, response);
            return;
        }


    }
}