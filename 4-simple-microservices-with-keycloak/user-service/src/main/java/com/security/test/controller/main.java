package com.security.test.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequiredArgsConstructor
public class main {

    @GetMapping("/account")
    public ResponseEntity<String> accountPage(JwtAuthenticationToken jwt, Authentication auth) {
        return ResponseEntity.ok("Account of  "+  jwt.getPrincipal() + "   ( "+ jwt.getTokenAttributes().get("name") +" )  "+ jwt.getAuthorities()+ "  ");
    }

    @GetMapping("/me")
    public Authentication me(Authentication auth) {
        return auth;
    }



    @GetMapping("test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("User service work");
    }

}

