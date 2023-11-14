package com.example.securityservice.controller;

import com.example.securityservice.entity.User;
import com.example.securityservice.service.UserAuthService;
import com.example.securityservice.dto.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAuthService service;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserAuthService userAuthService, AuthenticationManager authenticationManager) {
        this.service = userAuthService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/register")
    public User addNewUser(@RequestBody User user) {
        return service.store(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return service.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
