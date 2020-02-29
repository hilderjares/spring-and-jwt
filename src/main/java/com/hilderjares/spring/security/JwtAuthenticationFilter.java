package com.hilderjares.spring.security;

import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hilderjares.spring.entity.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.hilderjares.spring.security.SecurityConstants.EXPIRATION_TIME;
import static com.hilderjares.spring.security.SecurityConstants.HEADER_STRING;
import static com.hilderjares.spring.security.SecurityConstants.SECRET;
import static com.hilderjares.spring.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        var name = request.getAttribute("username");
        var password = request.getAttribute("password");

        System.err.println(name);
        System.err.println(password);

        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(null, null));
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain chain, final Authentication auth)
            throws IOException, ServletException {

        final String token = JWT.create().withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
