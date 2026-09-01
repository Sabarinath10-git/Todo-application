package com.example.demo;
import java.io.IOException;
import java.util.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
    public class jwtfilter extends OncePerRequestFilter {
    @Autowired
    public jwtutil jwtutil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authheader = request.getHeader("Authorization");

        System.out.println("REQUEST: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("AUTH HEADER: " + authheader);

        if (authheader != null && authheader.startsWith("Bearer ")) {

            String token = authheader.substring(7);

            System.out.println("TOKEN FOUND");

            if (jwtutil.validateToken(token)) {

                System.out.println("TOKEN VALID");

                String email = jwtutil.extractEmail(token);

                System.out.println("EMAIL: " + email);

                var auth = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of()
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

            } else {
                System.out.println("TOKEN INVALID");
            }
        }

        filterChain.doFilter(request, response);
    }
}