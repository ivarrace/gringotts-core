package com.ivarrace.gringotts.infrastructure.security.spring.adapter;

import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.security.spring.model.SpringUser;
import com.ivarrace.gringotts.infrastructure.security.spring.model.SpringUserMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SpringContextAdapter implements AuthPort {

    @Override
    public User getCurrentUser() {
        SpringUser contextSpringUser =
                (SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return SpringUserMapper.toDomain(contextSpringUser);
    }

}
