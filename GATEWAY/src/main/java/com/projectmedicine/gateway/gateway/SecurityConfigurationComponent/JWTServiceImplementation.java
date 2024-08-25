package com.projectmedicine.gateway.gateway.SecurityConfigurationComponent;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import static com.projectmedicine.gateway.gateway.Utils.Logs.ConsultationsLog.*;

@Log4j2
@Service
public class JWTServiceImplementation {
    /*------------------------------------------  VARIABLES -----------------------------------------------------------*/

    @Value(SECRET_KEY)
    private String secretKey;
    @Value("" + TOKEN_ACCESS_VALIDITY_TIME)
    private long jwtExpiration;



    /*------------------------------------------  EXTRACT EMAIL -----------------------------------------------------------*/

    /**
     * Extracts the user email from the given JWT token.
     *
     * @param token The JWT token from which to extract the user email.
     * @return The user email extracted from the token.
     */
    public String extractUserEmail(String token) {
        log.info("Extracting user email from token: {}", token);
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (SignatureException e) {
            throw new SignatureException(e.getMessage());
        }
    }

    /*------------------------------------------ PROCESSING CLAIMS -----------------------------------------------------------*/

    /**
     * Generic method to extract a specific claim from a JWT token.
     *
     * @param token         The JWT token from which to extract the claim.
     * @param claimResolver A function to resolve the desired claim from the token's claims.
     * @param <T>           The type of the extracted claim.
     * @return The extracted claim value.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        /* Commented above for handling token expired exception below*/
        log.info("Extracting all claims from token: {}", token);

        try {
            final Claims claims = extractAllClaims(token);
            return claimResolver.apply(claims);
        } catch (ExpiredJwtException ex) {
            throw new ExpiredJwtException(null, null, TOKEN_EXPIRED_EXCEPTION + ex.getMessage());
        } catch (SignatureException e) {
            throw new SignatureException(e.getMessage());
        }
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token from which to extract all claims.
     * @return All claims contained in the token.
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new SignatureException(e.getMessage());
        }
    }

    /*------------------------------------------  ENCRYPT-----------------------------------------------------------*/

    /**
     * Retrieves the signing key for JWT verification.
     *
     * @return The signing key for JWT verification.
     */
    private Key getSignInKey() {
        log.info("Retrieving signing key for JWT verification.");
        try {
            byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (SignatureException e) {
            throw new SignatureException(e.getMessage());
        }
    }

    /*------------------------------------------ TOKEN GENERATING -----------------------------------------------------------*/

    /**
     * Generates a JWT token for the given UserDetails.
     *
     * @param userDetails The UserDetails for which to generate the JWT token.
     * @return The generated JWT token.
     */

    public String generateToken(UserDetails userDetails) {
        log.info("Generating JWT token for user: {}", userDetails.getUsername());
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with additional claims for the given UserDetails.
     *
     * @param extraClaims Additional claims to be included in the JWT token.
     * @param userDetails The UserDetails for which to generate the JWT token.
     * @return The generated JWT token.
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        log.info("Generating JWT token with extra claims for user: {}", userDetails.getUsername());
        return buildToken(extraClaims, userDetails, jwtExpiration);

    }



    /*------------------------------------------ BUILD TOKEN -----------------------------------------------------------*/

    /**
     * Builds a JWT token with the specified extra claims, user details, and expiration time.
     *
     * @param extraClaims Additional claims to be included in the JWT token.
     * @param userDetails The UserDetails for which to generate the JWT token.
     * @param expiration  The expiration time for the JWT token in milliseconds.
     * @return The built JWT token.
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        log.info("Building token for user: {}", userDetails.getUsername());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /*------------------------------------------ CHECK TOKEN -----------------------------------------------------------*/

    /**
     * Checks if the given token is valid for the provided UserDetails.
     *
     * @param token       The JWT token to be validated.
     * @param userDetails The UserDetails against which to validate the token.
     * @return True if the token is valid for the provided UserDetails, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.info("Validating token for user: {}", userDetails.getUsername());

        try {
            final String userEmail = extractUserEmail(token);
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);

            return userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (SignatureException e) {
            throw new SignatureException(e.getMessage());
        }
    }

    /**
     * Checks if the given token has expired.
     *
     * @param token The JWT token to be checked for expiration.
     * @return True if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        log.info("Checking if token is expired: {}", token);
        Date expirationDate = extractExpiration(token);

        if (expirationDate.before(new Date())) {
            log.error("Token is expired: {}", token);
            //  throw new ExpiredJwtException(TOKEN_EXPIRED_EXCEPTION);
            return true;
        }


        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token from which to extract the expiration date.
     * @return The expiration date extracted from the token.
     */
    private Date extractExpiration(String token) {
        log.info("Extracting expiration date from token: {}", token);
        return extractClaim(token, Claims::getExpiration);
    }
}
