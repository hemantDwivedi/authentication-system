package com.assignment.authenticationsystem.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {
    public String generateToken(Authentication authentication) {
        long jwtExpiration = 86400000L;
        Instant expireAt = Instant.now().plusMillis(jwtExpiration);
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .setExpiration(Date.from(expireAt))
                .signWith(key())
                .compact();
    }


    public String getUsername(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String refreshToken(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    private Key key() {
        String jwtSecret = "f891cfcefd807726f4ebf246f65c0edf4591aaba316255c50dcbb4abfa1f1e91";
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateToken(String token) {
        Jwts
                .parserBuilder()
                .setSigningKey(key())
                .build()
                .parse(token);

        return true;
    }
}
