package com.ivarrace.gringotts.infrastructure.security.spring.controller;

import com.ivarrace.gringotts.infrastructure.security.spring.services.AuthHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auth. management")
public class AuthController {

    private final AuthHandler authHandler;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid AuthRequest request) {
        log.info("Login request");
        return authHandler.login(request);
    }
}
