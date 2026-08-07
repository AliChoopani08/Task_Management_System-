package com.Ali_Choopani.Task_Managment_System.security;

import com.Ali_Choopani.Task_Managment_System.exceptions.NotFoundSecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import static io.jsonwebtoken.Jwts.parser;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.lang.Long.valueOf;
import static java.time.Duration.ofMinutes;
import static java.time.Instant.now;
import static java.util.Date.from;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService{

    private static final String SECRET_KEY_NAME = "SECRET_KEY_ACCESS_TOKEN";
    private final String secretKey;

    public JwtServiceImpl() {
        secretKey = System.getenv(SECRET_KEY_NAME);
        if (secretKey == null || secretKey.isEmpty()) {
            final NotFoundSecretKey ex = new NotFoundSecretKey(SECRET_KEY_NAME);
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public String generateToken(Long userId, String userRole) {
        final Duration duration = ofMinutes(15);
        Instant tokenExpirationDate = now().plus(duration);

        return Jwts.builder()
                .subject(userId.toString())
                .claim("User role", userRole)
                .issuedAt(from(now()))
                .expiration(from(tokenExpirationDate))
                .signWith(generateTokenSignature(secretKey))
                .compact();
    }

    @Override
    public Long extractId(String token) {
        return valueOf(parser()
                .verifyWith(generateTokenSignature(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }

    @Override
    public boolean isTokenExpired(String token) {
        final Date expirationToken = parser()
                .verifyWith(generateTokenSignature(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expirationToken.before(new Date());
    }

    @Override
    public boolean isTokenValid(String token, Long userId) {

        return Objects.equals(extractId(token), userId) && !isTokenExpired(token);
    }


    private SecretKey generateTokenSignature(String secretKey) {
        final byte[] decodeKey = Decoders.BASE64.decode(secretKey);

        return hmacShaKeyFor(decodeKey);
    }


}
