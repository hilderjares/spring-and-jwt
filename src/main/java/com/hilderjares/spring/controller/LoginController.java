package com.hilderjares.spring.controller;

import com.hilderjares.spring.entity.UserDomain;
import com.hilderjares.spring.security.Response;
import com.hilderjares.spring.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private JwtService jwtService;

    private String token;

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDomain user) {
        try {
            this.token = jwtService.authentication(user);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(
                new Response("Incorrect username or password", 404), HttpStatus.NOT_FOUND
            );
        }
        return ResponseEntity.ok(new Response(this.token, 200));
    }
}
