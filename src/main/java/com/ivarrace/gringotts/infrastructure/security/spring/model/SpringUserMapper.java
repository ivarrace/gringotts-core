package com.ivarrace.gringotts.infrastructure.security.spring.model;

import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.domain.user.UserAuthority;

import java.util.Optional;
import java.util.Set;

public class SpringUserMapper {

    private SpringUserMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static User toDomain(SpringUser springUser) {
        if (springUser == null) {
            return null;
        }
        User domain = new User();
        domain.setId(springUser.getId());
        domain.setCreatedDate(springUser.getCreatedDate());
        domain.setLastModified(springUser.getLastModified());
        domain.setUsername(springUser.getUsername());
        domain.setPassword(springUser.getPassword());
        domain.setAuthority(
                springUser.getAuthorities().stream()
                        .map(auth -> UserAuthority.valueOf(auth.getAuthority()))
                        .findFirst()
                        .orElseThrow());
        domain.setEnabled(springUser.isEnabled());
        domain.setNonExpired(springUser.isNonExpired());
        domain.setNonLocked(springUser.isNonLocked());
        domain.setCredentialNonExpired(springUser.isCredentialNonExpired());
        return domain;
    }

    public static Optional<User> toDomain(Optional<SpringUser> userEntity) {
        if (!userEntity.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(toDomain(userEntity.get()));
    }

    public static SpringUser toContext(User domain) {
        if (domain == null) {
            return null;
        }
        SpringUser entity = new SpringUser();
        entity.setId(domain.getId());
        entity.setCreatedDate(domain.getCreatedDate());
        entity.setLastModified(domain.getLastModified());
        entity.setUsername(domain.getUsername());
        entity.setPassword(domain.getPassword());
        entity.setAuthorities(Set.of(new SpringRole(domain.getAuthority().name())));
        entity.setEnabled(domain.isEnabled());
        entity.setNonExpired(domain.isNonExpired());
        entity.setNonLocked(domain.isNonLocked());
        entity.setCredentialNonExpired(domain.isCredentialNonExpired());
        return entity;
    }
}

