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

    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal OidcUser userDetails) {
        if (userDetails == null) {
            return "Not logged in";
        }
        return "Hello "+  userDetails.getFullName() +  "   "+ userDetails.getEmail()+ "  "+ userDetails.getAuthorities();
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> dashboardPage(JwtAuthenticationToken jwt) {
        return ResponseEntity.ok("Only admin Dashboard of  "+   jwt.getTokenAttributes().get("name") +  "   "+ jwt.getTokenAttributes().get("email")+ "  "+ jwt.getAuthorities()+ "   ");
    }


    @GetMapping("/account")
    public ResponseEntity<String> accountPage(JwtAuthenticationToken jwt, Authentication auth) {
        return ResponseEntity.ok("Account of  "+  jwt.getPrincipal() + "   ( "+ jwt.getTokenAttributes().get("name") +" )  "+ jwt.getAuthorities()+ "  ");
    }

    @GetMapping("/me")
    public Authentication me(Authentication auth) {
        return auth;
    }


    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }





}

