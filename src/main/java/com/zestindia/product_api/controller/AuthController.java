package com.zestindia.product_api.controller;

import com.zestindia.product_api.dto.auth.LoginRequest;
import com.zestindia.product_api.dto.auth.LoginResponse;
import com.zestindia.product_api.dto.auth.RefreshTokenRequest;
import com.zestindia.product_api.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(
            @Valid @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(
                authService.refresh(
                        request.getRefreshToken()
                )
        );
    }
}