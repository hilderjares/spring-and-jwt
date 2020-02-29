package com.hilderjares.spring.service;

import com.auth0.jwt.JWT;
import com.hilderjares.spring.entity.UserDomain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.hilderjares.spring.security.SecurityConstants.EXPIRATION_TIME;
import static com.hilderjares.spring.security.SecurityConstants.SECRET;

import java.util.Date;

@Service
public class JwtService {

    @Autowired
    private AuthenticationManager authenticationManager;

    private String token;

    public String authentication(UserDomain user) throws BadCredentialsException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        this.token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                        .sign(HMAC512(SECRET.getBytes()));

        return this.token;
    }
}
