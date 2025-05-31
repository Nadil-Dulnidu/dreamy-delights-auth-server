package com.dreamy_delights.root.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username, 1000 * 60 * 10);
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, 1000 * 60 * 60 * 24 * 7);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationMillis) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public List<GrantedAuthority> validateToken(String token) {
        if (!isTokenExpired(token)) {
            final Claims claims = extractAllClaims(token);
            String role = (String) claims.get("role");
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(() -> role);
            return authorities;
        }
        return new ArrayList<>();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Optional<String> extractUsernameOpt(String token) {
        if(!isTokenExpired(token)){
            return Optional.of(extractAllClaims(token).getSubject());
        }
        return Optional.empty();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}