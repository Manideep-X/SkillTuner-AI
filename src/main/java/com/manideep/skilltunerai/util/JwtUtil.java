package com.manideep.skilltunerai.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.manideep.skilltunerai.exception.JwtExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String secretJWTKey;
    private final int expiryDuration = 1000*60*60*24; // 1 day(24hrs) validity

    public JwtUtil(@Value("${jwt.secret}") String secretJWTKey) {
        this.secretJWTKey = secretJWTKey;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretJWTKey.getBytes());
    }

    private Claims getAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException("JWT token is expired: "+e.getStackTrace());
        }
    }

    public String getUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    public Boolean isTokenExpired(String token) {
        return getAllClaims(token).getExpiration().before(new Date());
    }

    // First need to check if the token's username matches with the userDetails, then if the token expired.
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        return (getUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiryDuration))
                .signWith(getSigningKey()) // For signature we need to sign the secret key with HS256
                .compact(); // This generate the final JWT token
    }

    public int getExpirationDurationInMilliSec() {
        return expiryDuration;
    }

}
