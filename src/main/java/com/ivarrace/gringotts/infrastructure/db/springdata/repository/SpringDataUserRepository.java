package com.ivarrace.gringotts.infrastructure.db.springdata.repository;

import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);
}
