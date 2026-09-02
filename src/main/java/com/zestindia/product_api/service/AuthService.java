package com.zestindia.product_api.service;

import com.zestindia.product_api.dto.auth.LoginRequest;
import com.zestindia.product_api.dto.auth.LoginResponse;
import com.zestindia.product_api.entity.RefreshToken;
import com.zestindia.product_api.entity.User;
import com.zestindia.product_api.repository.RefreshTokenRepository;
import com.zestindia.product_api.repository.UserRepository;
import com.zestindia.product_api.security.JwtService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final long refreshTokenExpiration;

    public AuthService(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            @Value("${jwt.refresh-token-expiration}")
            long refreshTokenExpiration) {

        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid username or password"
                        )
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid username or password"
            );
        }

        String accessToken =
                jwtService.generateAccessToken(user);

        String refreshToken =
                createRefreshToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken
        );
    }

    public LoginResponse refresh(String oldToken) {

        RefreshToken stored =
                refreshTokenRepository
                        .findByToken(oldToken)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid refresh token"
                                )
                        );

        if (stored.isRevoked() ||
                stored.getExpiryDate()
                        .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Refresh token expired or revoked"
            );
        }

        User user = stored.getUser();

        // Rotation:
        // invalidate old refresh token
        stored.setRevoked(true);
        refreshTokenRepository.save(stored);

        String newAccessToken =
                jwtService.generateAccessToken(user);

        String newRefreshToken =
                createRefreshToken(user);

        return new LoginResponse(
                newAccessToken,
                newRefreshToken
        );
    }

    private String createRefreshToken(User user) {

        String token = UUID.randomUUID().toString()
                + UUID.randomUUID();

        RefreshToken refreshToken =
                new RefreshToken();

        refreshToken.setToken(token);
        refreshToken.setUser(user);
        refreshToken.setRevoked(false);
        refreshToken.setExpiryDate(
                LocalDateTime.now()
                        .plusSeconds(
                                refreshTokenExpiration / 1000
                        )
        );

        refreshTokenRepository.save(refreshToken);

        return token;
    }
}