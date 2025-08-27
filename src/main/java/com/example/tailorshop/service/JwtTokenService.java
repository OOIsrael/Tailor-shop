package com.example.tailorshop.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {
    private static final String SECRET_KEY = "secret-key";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
