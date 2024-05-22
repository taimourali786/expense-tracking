package com.cotech.helpdesk.security.service;

import com.cotech.helpdesk.jpa.userauth.UserAuthEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Generate via online website
    // Encryption 256
    private final String secretKey;

    public JwtService(final @Value("${jwt.sign-in-key}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String getUsernameFromToken(final String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenExpired(final String token) {
        return this.extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    public boolean isValidToken(final String token, final UserAuthEntity user) {
        String username = this.getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(final UserAuthEntity userAuth) {
        return this.generateToken(new HashMap<>(), userAuth);
    }

    public String generateToken(final Map<String, String> extraClaims,
                                final UserAuthEntity userAuth) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userAuth.getUsername())
                // Subject should have unique identifier of user for which the token is
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Claims extractAllClaims(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
