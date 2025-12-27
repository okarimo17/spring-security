package com.security.test.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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
    public String dashboardPage(@AuthenticationPrincipal OidcUser userDetails) {
        return "Only admin Dashboard of  "+   userDetails.getFullName() +  "   "+ userDetails.getEmail()+ "  "+ userDetails.getAuthorities()+ "   ";
    }


    @GetMapping("/account")
    public String accountPage(@AuthenticationPrincipal OidcUser userDetails, Authentication auth) {
        return "Account of  "+  userDetails.getFullName() +  "   "+ userDetails.getEmail()+ "  ";
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

