package com.example.demo;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.*;
import java.lang.*;

import java.security.Key;
import java.util.Date;

@Component
public class jwtutil {
    private final String Secret = "hello its the passsowrd authentication";
    private final long Expiration = 1000 * 60 * 60;
    private final Key secrkey = Keys.hmacShaKeyFor(Secret.getBytes());

    //token generation
    public String genToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Expiration))
                .signWith(secrkey)
                .compact();
    }

    public String extractEmail(String token){
       return Jwts.parser()
                .setSigningKey(secrkey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .getSubject();
    }
    //validation of generated token
    public boolean validateToken(String token) {
        try {
          extractEmail(token);
            return true;

        } catch (JwtException exception) {
            return false;
        }

    }
}
