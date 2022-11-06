package com.ivarrace.gringotts.infrastructure.db.springdata.mapper;

import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.domain.user.UserAuthority;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        User domain = new User();
        domain.setId(entity.getId().toString());
        domain.setCreatedDate(entity.getCreatedDate());
        domain.setLastModified(entity.getLastModified());
        domain.setUsername(entity.getUsername());
        domain.setPassword(entity.getPassword());
        domain.setAuthority(entity.getAuthority() != null ?
                UserAuthority.valueOf(entity.getAuthority()) : null);
        domain.setEnabled(entity.isEnabled());
        domain.setNonExpired(entity.isNonExpired());
        domain.setNonLocked(entity.isNonLocked());
        domain.setCredentialNonExpired(entity.isCredentialNonExpired());
        return domain;
    }

    public static Optional<User> toDomain(Optional<UserEntity> userEntity) {
        if (!userEntity.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(toDomain(userEntity.get()));
    }

    public static UserEntity toDbo(User domain) {
        if (domain == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId() != null ?
                UUID.fromString(domain.getId()) : null);
        entity.setCreatedDate(domain.getCreatedDate());
        entity.setLastModified(domain.getLastModified());
        entity.setUsername(domain.getUsername());
        entity.setPassword(domain.getPassword());
        entity.setAuthority(domain.getAuthority() != null ?
                domain.getAuthority().name() : null);
        entity.setEnabled(domain.isEnabled());
        entity.setNonExpired(domain.isNonExpired());
        entity.setNonLocked(domain.isNonLocked());
        entity.setCredentialNonExpired(domain.isCredentialNonExpired());
        return entity;
    }
}

