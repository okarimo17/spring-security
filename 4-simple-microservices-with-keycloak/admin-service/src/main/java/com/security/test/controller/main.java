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


    @GetMapping()
    public ResponseEntity<String> dashboardPage(JwtAuthenticationToken jwt) {
        return ResponseEntity.ok("Only admin can see this page, User info =  "+   jwt.getTokenAttributes().get("name") +  "   "+ jwt.getTokenAttributes().get("email")+ "  "+ jwt.getAuthorities()+ "   ");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Admin service work");
    }

}

