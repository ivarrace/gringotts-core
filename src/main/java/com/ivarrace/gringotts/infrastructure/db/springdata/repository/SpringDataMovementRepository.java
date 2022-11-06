package com.ivarrace.gringotts.infrastructure.db.springdata.repository;

import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataMovementRepository extends JpaRepository<MovementEntity, UUID> {

    List<MovementEntity> findAllByCategory_key(String categoryKey); //NOSONAR
}
