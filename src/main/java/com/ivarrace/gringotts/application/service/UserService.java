package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.ports.data.UserRepositoryPort;
import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService {

    private final AuthPort authPort;
    private final UserRepositoryPort userRepositoryPort;

    public UserService(AuthPort authPort, UserRepositoryPort userRepositoryPort) {
        this.authPort = authPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    public User save(User user) {
        return userRepositoryPort.save(user);
    }
}
