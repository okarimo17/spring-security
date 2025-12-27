package com.security.test.service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${jwt.expiration}")
    private Integer expirationInMinutes;


    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public  String Login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        Authentication cred = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(cred);
        String token = jwtService.generateToken(username,password);
        assignCookieToResponse(response, token);
        return "Authenticated successfully";
    }


    public void assignCookieToResponse(HttpServletResponse response, String token){
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge( expirationInMinutes * 60 - 60);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }
}
