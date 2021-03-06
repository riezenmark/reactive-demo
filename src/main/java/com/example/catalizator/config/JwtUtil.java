package com.example.catalizator.config;

import com.example.catalizator.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expirationTime;

    private JwtParser jwtParser;

    @PostConstruct
    public void initJvtParser() {
        String key = Base64.getEncoder().encodeToString(secret.getBytes());
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String extractUsername(String authToken) {
        return getClaimsFromToken(authToken)
                .getSubject();
    }

    public boolean tokenIsValid(String authToken) {
        return getClaimsFromToken(authToken)
                .getExpiration()
                .after(new Date());
    }

    public Claims getClaimsFromToken(String authToken) {
        return jwtParser
                .parseClaimsJws(authToken)
                .getBody();
    }

    public String generateToken(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", List.of(user.getRole()));

        long expirationSeconds = Long.parseLong(expirationTime);
        Date creationDate = new Date();
        Date expirationDate = new Date(creationDate.getTime() + expirationSeconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}
