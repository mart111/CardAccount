package com.example.demo.security.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.model.Role;
import com.example.demo.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    private String secretKey = "secretKeyMartin";
    private String issuer;

    private final MyUserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        issuer = UUID.randomUUID().toString();
    }

    public String createToken(String username, List<Role> roles) {

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_00))
                .withClaim("role", roles.get(0).getRole())
                .sign(Algorithm.HMAC512(secretKey));

    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return JWT.decode(token)
                .getSubject();
    }

    public String resolveToken(HttpServletRequest req) throws IOException {
        String token = req.getHeader("Authorization");
        if (token != null && validateToken(token))
            return token;

        return null;
    }

    public boolean validateToken(String token) throws IOException {

        try {
            JWT.require(Algorithm.HMAC512(secretKey))
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token was expired or not valid");
        }
    }
}
