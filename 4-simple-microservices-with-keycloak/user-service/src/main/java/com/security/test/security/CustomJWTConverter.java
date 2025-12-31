package com.security.test.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CustomJWTConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwtSource) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Map<String, Object> realmAccess = (Map<String, Object>) jwtSource.getClaim("realm_access");
        if(realmAccess != null){
            List<String> roles = (List<String>) realmAccess.get("roles");
            for(String role : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role));
            }
        }
        return new JwtAuthenticationToken(
                jwtSource,
                grantedAuthorities,
                jwtSource.getSubject()
        );
    }
}