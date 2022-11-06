package com.ivarrace.gringotts.infrastructure.db.springdata.repository;

import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.CategoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataCategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    List<CategoryEntity> findAllByGroup_key(String groupKey); //NOSONAR

    Optional<CategoryEntity> findByKeyAndGroup_key(String categoryKey, String groupKey); //NOSONAR
}
