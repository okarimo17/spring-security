package com.security.test.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;



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
                                        .requestMatchers("/","/login","/me","favicon.ico").permitAll()         // Allow root
                                        .requestMatchers("/dashboard").authenticated()    // MUST allow login page
                                        .anyRequest().authenticated()             // Everything else locked
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
