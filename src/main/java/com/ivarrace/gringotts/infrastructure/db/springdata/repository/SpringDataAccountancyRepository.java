package com.ivarrace.gringotts.infrastructure.db.springdata.repository;

import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.AccountancyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataAccountancyRepository extends JpaRepository<AccountancyEntity, UUID> {

    List<AccountancyEntity> findAllByUsers_UserId(UUID userId); //NOSONAR

    Optional<AccountancyEntity> findByKeyAndUsers_UserId(String accountancyKey, UUID userId); //NOSONAR
}
