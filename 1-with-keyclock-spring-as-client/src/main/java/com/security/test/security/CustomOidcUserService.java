package com.security.test.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser =  super.loadUser(userRequest);
        Set<GrantedAuthority> grantedAuthorities =  new HashSet<GrantedAuthority>(oidcUser.getAuthorities());

        Map<String, Object> realmAccess = (Map<String, Object>) oidcUser.getAttributes().get("realm_access");
        if(realmAccess!= null) {
            List<String> roles = (List<String>) realmAccess.get("roles");
            for(String role : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role));
            }
        }

        return new DefaultOidcUser(
            grantedAuthorities,
            oidcUser.getIdToken(),
            oidcUser.getUserInfo()
        );
    }
}