package com.ivarrace.gringotts.infrastructure.security.spring.services;

import com.ivarrace.gringotts.infrastructure.security.spring.controller.AuthRequest;
import com.ivarrace.gringotts.infrastructure.security.spring.controller.AuthResponse;
import com.ivarrace.gringotts.infrastructure.security.spring.jwt.JwtTokenUtil;
import com.ivarrace.gringotts.infrastructure.security.spring.model.SpringUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthHandler {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthHandler(AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public AuthResponse login(AuthRequest request){
        Authentication authenticate = authenticationManager
                .authenticate(request.buildAuthenticationToken());
        SpringUser springUser = (SpringUser) authenticate.getPrincipal();
        AuthResponse response = new AuthResponse();
        String token = jwtTokenUtil.generateAccessToken(springUser);
        response.setAccessToken("Bearer " + token);
        response.setExpiresAt(jwtTokenUtil.getExpirationDate(token));
        return response;
    }

}