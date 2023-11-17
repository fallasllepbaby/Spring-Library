package com.example.securityservice.controller;

import com.example.securityservice.entity.User;
import com.example.securityservice.service.UserAuthService;
import com.example.securityservice.dto.AuthRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Register new user", description = "Register new user", tags = { "Auth" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @PostMapping("/register")
    public User addNewUser(@Parameter(description = "User info for registration", required = true) @RequestBody User user) {
        return service.store(user);
    }

    @Operation(summary = "Get token", description = "Get token", tags = { "Auth" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @PostMapping("/token")
    public String getToken(@Parameter(description = "AuthRequest info for getting token", required = true) @RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return service.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @Operation(summary = "Validate token", description = "Validate token", tags = { "Auth" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping("/validate")
    public String validateToken(@Parameter(description = "Token for validating", required = true) @RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
