package com.hybridpos.api_gateway.config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component

public class JwtUtil {

    private static final String SECRET_KEY =

            "hybrid-pos-system-very-secret-key-top-secret";

    private final Key key =

            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public Claims extractClaims(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(key)

                .build()

                .parseClaimsJws(token)

                .getBody();

    }

    public String extractUsername(String token) {

        return extractClaims(token).getSubject();

    }

    public String extractRole(String token) {

        return extractClaims(token).get("role", String.class);

    }

    public boolean isTokenValid(String token) {

        try {

            Claims claims = extractClaims(token);

            return claims.getExpiration().after(new Date());

        } catch (Exception e) {

            return false;

        }

    }

}