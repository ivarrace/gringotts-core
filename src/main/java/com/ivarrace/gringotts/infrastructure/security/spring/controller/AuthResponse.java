package com.ivarrace.gringotts.infrastructure.security.spring.controller;

import java.time.LocalDateTime;

public class AuthResponse {

    private String accessToken;
    private LocalDateTime expiresAt;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
