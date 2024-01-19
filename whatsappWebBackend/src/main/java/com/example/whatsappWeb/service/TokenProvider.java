package com.example.whatsappWeb.service;

import com.example.whatsappWeb.config.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenProvider {
        public String generateToken(Authentication authentication){
            String email = authentication.getName();
            SecretKey key= Keys.hmacShaKeyFor(JwtConstant.secret_key.getBytes());

            // Define the expiration time (24 hours from now)
            long expirationTimeMillis = System.currentTimeMillis() + 86400000;

            // Generate the JWT
            String jwt = Jwts.builder()
                    .setIssuer("EncodeMe")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(expirationTimeMillis))
                    .claim("email", email)
                    .signWith(key)
                    .compact();
            return jwt;
        }
        public String getEmailFromToken(String jwt){
            jwt=jwt.substring(7);
            SecretKey key= Keys.hmacShaKeyFor(JwtConstant.secret_key.getBytes());
            Claims claim= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            String email=String.valueOf(claim.get("email"));
            return email;
        }

}
