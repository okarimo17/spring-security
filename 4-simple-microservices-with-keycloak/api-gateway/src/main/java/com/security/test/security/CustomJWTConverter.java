package com.security.test.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomJWTConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwtSource) {
        // Extract roles from realm_access
        Map<String, Object> realmAccess = jwtSource.getClaim("realm_access");
        Collection<GrantedAuthority> authorities = List.of();

        if (realmAccess != null && realmAccess.containsKey("roles")) {
            List<String> roles = (List<String>) realmAccess.get("roles");
            authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        }

        // Return the token wrapped in a Mono
        return Mono.just(new JwtAuthenticationToken(
                jwtSource,
                authorities,
                jwtSource.getSubject()
        ));
    }
}
//public class CustomJWTConverter implements Converter<Jwt, AbstractAuthenticationToken> {
//
//    @Override
//    public AbstractAuthenticationToken convert(Jwt jwtSource) {
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        Map<String, Object> realmAccess = (Map<String, Object>) jwtSource.getClaim("realm_access");
//        if(realmAccess != null){
//            List<String> roles = (List<String>) realmAccess.get("roles");
//            for(String role : roles) {
//                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role));
//            }
//        }
//        return new JwtAuthenticationToken(
//                jwtSource,
//                grantedAuthorities,
//                jwtSource.getSubject()
//        );
//    }
//}