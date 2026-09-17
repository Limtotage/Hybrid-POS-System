package com.hybridpos.product_service.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

        private final JwtUtil jwtUtil;

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) {
                String path = request.getServletPath();

                return path.startsWith("/uploads/products/")
                                || path.equals("/actuator/health")
                                || path.equals("/actuator/info");
        }

        @Override
        protected void doFilterInternal(
                        @NonNull HttpServletRequest request,
                        @NonNull HttpServletResponse response,
                        @NonNull FilterChain filterChain)
                        throws ServletException, IOException {

                String authHeader = request.getHeader("Authorization");

                if (authHeader != null &&
                                authHeader.startsWith("Bearer ")) {

                        String jwt = authHeader.substring(7);

                        try {

                                if (jwtUtil.validateToken(jwt)) {

                                        String username = jwtUtil.extractUsername(jwt);

                                        String role = jwtUtil.extractRole(jwt);

                                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                                        System.out.println("JWT USERNAME: " + username);
                                        System.out.println("JWT ROLE: " + role);
                                        System.out.println("AUTHORITY: " + authority.getAuthority());

                                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                                        username,
                                                        null,
                                                        java.util.List.of(authority));

                                        SecurityContextHolder
                                                        .getContext()
                                                        .setAuthentication(authentication);
                                }

                        } catch (Exception e) {

                                SecurityContextHolder
                                                .clearContext();
                        }
                }

                filterChain.doFilter(request, response);
        }
}