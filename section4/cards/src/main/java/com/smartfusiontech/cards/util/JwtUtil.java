package com.smartfusiontech.cards.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.security.Key;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private long expiration; // e.g. 86400000 for 1 day

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateToken(String token, String username) {
    final String extractedUsername = extractUsername(token);
    return (username.equals(extractedUsername) && !isTokenExpired(token));
  }

  public String extractUsername(String token) {
    return extractClaims(token).getSubject();
  }

  private boolean isTokenExpired(String token) {
    return extractClaims(token).getExpiration().before(new Date());
  }

  private Claims extractClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }
}

