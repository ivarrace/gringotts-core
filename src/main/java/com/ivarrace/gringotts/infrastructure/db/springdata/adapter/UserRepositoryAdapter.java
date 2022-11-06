package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.application.exception.UserAlreadyRegisteredException;
import com.ivarrace.gringotts.application.repository.UserRepositoryPort;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.UserEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.UserEntityMapper;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataUserRepository;
import com.ivarrace.gringotts.infrastructure.security.spring.port.AuthRepositoryPort;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryAdapter implements AuthRepositoryPort, UserRepositoryPort {

    private final SpringDataUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRepositoryAdapter(SpringDataUserRepository userRepository,
                                 @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return UserEntityMapper.toDomain(userRepository.findByUsername(username));
    }

    @Override
    public User save(User newUser) {
        Optional<User> user = this.findByUsername(newUser.getUsername());
        if (user.isPresent()) {
            throw new UserAlreadyRegisteredException(newUser.getUsername());
        }
        UserEntity toSave = UserEntityMapper.toDbo(newUser);
        toSave.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return UserEntityMapper.toDomain(userRepository.save(toSave));
    }
}
