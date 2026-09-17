package com.hybridpos.api_gateway.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component

@RequiredArgsConstructor

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override

    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain)

            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Login endpoint'i JWT gerektirmez

        if (path.equals("/api/auth/login")
        || path.equals("/actuator/health")
        || path.equals("/actuator/info")
        || path.startsWith("/uploads/products/")) {

            filterChain.doFilter(request, response);

            return;

        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            return;

        }

        String token = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(token)) {

            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            return;

        }

        String username = jwtUtil.extractUsername(token);

        String role = jwtUtil.extractRole(token);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(

                username,

                null,

                java.util.List.of(

                        new SimpleGrantedAuthority(role)));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }

}