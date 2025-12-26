package com.security.test.controller;


import com.security.test.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class main {

    private final AuthService authService;

    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "Not logged in";
        }
        return "Hello "+userDetails.getUsername();
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String dashboardPage(@AuthenticationPrincipal UserDetails userDetails) {
        return "Dashboard of  "+userDetails.getUsername()+ "  "+ userDetails.getAuthorities();
    }

    @GetMapping("/account")
    public String accountPage(@AuthenticationPrincipal UserDetails userDetails) {
        return "Account of  "+userDetails.getUsername()+ "  "+ userDetails.getAuthorities();
    }


    @GetMapping("/login")
    public String loginPage(@RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpServletResponse response) {


        return authService.Login(username, password, request, response);
    }



    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

}

