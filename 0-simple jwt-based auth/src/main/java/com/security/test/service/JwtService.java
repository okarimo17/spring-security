package com.security.test.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Integer expirationInMinutes;


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, String password) {
        return Jwts.builder()
                .subject(username)
                .signWith(this.getSigningKey())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(this.expirationInMinutes, ChronoUnit.MINUTES)))
                .compact();
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = userDetails.getUsername();
        String tokenOwner = extractUsername(token);
        Date expiration = extractExpirationDate(token);
        return username.equals(tokenOwner) && !expiration.before(new Date());
    }


    public boolean isTokenExpired(String token) {
        Date expiration = extractExpirationDate(token);
        return expiration.before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(this.getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public  <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = parseToken(token);
        return claimsTFunction.apply(claims);
    }



}
