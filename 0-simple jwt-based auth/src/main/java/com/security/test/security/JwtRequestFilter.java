package com.security.test.security;

import com.security.test.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.RequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getCookies() == null ) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = null;
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }

        if(token == null){
            filterChain.doFilter(request,response);
            return;
        }

        final String username;
        try {
            username = jwtService.extractUsername(token);
        }catch (Exception e){
            filterChain.doFilter(request, response);
            return;
        }

        if(username == null || SecurityContextHolder.getContext().getAuthentication() != null ){
            filterChain.doFilter(request,response);
            return;
        }


        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        boolean isTokenValid = jwtService.isTokenValid(token, userDetails);


        if(isTokenValid){
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request,response);

    }



//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getServletPath();
//        return path.equals("/favicon.ico");
//    }
}
