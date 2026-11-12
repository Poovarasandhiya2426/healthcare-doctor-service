
package com.healthcare.doctor.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    private SecretKey signingKey;

    // Validate JWT secret during application startup
    @PostConstruct
    public void initializeSigningKey() {

        if (jwtSecret == null || jwtSecret.isBlank()) {
            throw new IllegalStateException(
                    "JWT secret must not be empty."
            );
        }

        try {
            byte[] keyBytes = Decoders.BASE64URL.decode(jwtSecret);

            if (keyBytes.length < 32) {
                throw new IllegalStateException(
                        "JWT secret must contain at least 32 decoded bytes."
                );
            }

            this.signingKey = Keys.hmacShaKeyFor(keyBytes);

        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException(
                    "Invalid Base64URL JWT secret configuration.",
                    exception
            );
        }
    }

    // Parse and verify JWT token
    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Long extractUserId(String token) {

        Object userId = extractAllClaims(token).get("userId");

        if (userId == null) {
            return null;
        }

        if (userId instanceof Number number) {
            return number.longValue();
        }

        return Long.valueOf(userId.toString());
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Validate JWT token
    public boolean isTokenValid(String token) {

        try {
            Claims claims = extractAllClaims(token);

            Object userId = claims.get("userId");
            String role = claims.get("role", String.class);

            return claims.getSubject() != null
                    && !claims.getSubject().isBlank()
                    && isValidUserId(userId)
                    && role != null
                    && !role.isBlank()
                    && claims.getExpiration() != null;

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    private boolean isValidUserId(Object userId) {

        if (userId == null) {
            return false;
        }

        try {
            long id = Long.parseLong(userId.toString());
            return id > 0;

        } catch (NumberFormatException exception) {
            return false;
        }
    }
}
