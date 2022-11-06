package com.ivarrace.gringotts.infrastructure.security.spring.port;

import com.ivarrace.gringotts.domain.user.User;

import java.util.Optional;

public interface AuthRepositoryPort {

    Optional<User> findByUsername(String username);

}
