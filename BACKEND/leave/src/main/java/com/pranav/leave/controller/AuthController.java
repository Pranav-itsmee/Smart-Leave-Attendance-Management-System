package com.pranav.leave.controller;

import org.springframework.web.bind.annotation.*;

import com.pranav.leave.dto.request.*;
import com.pranav.leave.dto.response.AuthResponse;
import com.pranav.leave.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}