package com.hilderjares.spring.security;

import static com.hilderjares.spring.security.SecurityConstants.HEADER_STRING;
import static com.hilderjares.spring.security.SecurityConstants.SECRET;
import static com.hilderjares.spring.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private String user;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(HEADER_STRING);
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build();

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(HEADER_STRING);

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            this.user = jwtVerifier.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
        } catch (JWTDecodeException e) {
            chain.doFilter(request, response);
            return;
        } catch (TokenExpiredException e) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            this.user, null, Collections.emptyList()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
