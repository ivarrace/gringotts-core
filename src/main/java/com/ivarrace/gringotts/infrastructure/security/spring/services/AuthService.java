package com.ivarrace.gringotts.infrastructure.security.spring.services;

import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.adapter.UserRepositoryAdapter;
import com.ivarrace.gringotts.infrastructure.security.spring.model.SpringUserMapper;
import com.ivarrace.gringotts.infrastructure.security.spring.port.AuthRepositoryPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    private final AuthRepositoryPort authRepositoryPort;

    public AuthService(UserRepositoryAdapter authRepository) {
        this.authRepositoryPort = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = authRepositoryPort.findByUsername(username);
        if (user.isPresent()) {
            return SpringUserMapper.toContext(user.get());
        }
        throw new UsernameNotFoundException(username);
    }

}