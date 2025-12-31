package com.security.test.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {


    private  final  CustomJWTConverter customJWTConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomJWTConverter customJWTConverter) throws Exception {
        return http
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers("/test").permitAll()
                                        .anyRequest().hasRole("ADMIN")
                )
                .oauth2ResourceServer(oauth2ResourceServer ->
                    oauth2ResourceServer.jwt(jwtConfigurer ->
                        jwtConfigurer.jwtAuthenticationConverter(customJWTConverter)
                    )
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }




}
