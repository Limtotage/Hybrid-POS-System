package com.hybridpos.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration

@RequiredArgsConstructor

public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean

        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http

                                .csrf(csrf -> csrf.disable())

                                .sessionManagement(session -> session

                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                .authorizeHttpRequests(auth -> auth
                                                .anyRequest().permitAll());

                http.addFilterBefore(

                                jwtAuthenticationFilter,

                                UsernamePasswordAuthenticationFilter.class);

                return http.build();

        }

}