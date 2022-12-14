package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.ports.data.UserRepositoryPort;
import com.ivarrace.gringotts.domain.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService {

    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public User save(User user) {
        return userRepositoryPort.save(user);
    }
}
