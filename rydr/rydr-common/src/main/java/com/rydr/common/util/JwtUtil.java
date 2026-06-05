package com.rydr.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Utility class for JSON Web Token (JWT) generation, parsing, and verification.
 *
 * @author Rydr Team
 */
public class JwtUtil {

    /** Default token validity period: 30 days in milliseconds */
    private static final long EXPIRATION_TIME_MS = 1000L * 60 * 60 * 24 * 30;

    /**
     * Secret key, stored only on the server side.
     * IMPORTANT: Override via environment variable JWT_SECRET in production.
     */
    private static final String SECRET = System.getenv("JWT_SECRET") != null
            ? System.getenv("JWT_SECRET") : "changeme-override-in-production";

    /**
     * Create a signed JWT token.
     *
     * @param subject   the subject claim (e.g. user ID or phone number)
     * @param issueDate the token issue date
     * @return the signed JWT token string
     */
    public static String createToken(String subject, Date issueDate) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issueDate)
                .setExpiration(new Date(issueDate.getTime() + EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * Parse and validate a JWT token string.
     *
     * @param token the JWT token to parse
     * @return the subject claim, or empty string if invalid or expired
     */
    public static String parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            if (claims != null) {
                return claims.getSubject();
            }
        } catch (ExpiredJwtException e) {
            System.err.println("[JwtUtil] Token validation failed: token has expired");
        } catch (Exception e) {
            System.err.println("[JwtUtil] Token validation failed: " + e.getMessage());
        }
        return "";
    }
}

