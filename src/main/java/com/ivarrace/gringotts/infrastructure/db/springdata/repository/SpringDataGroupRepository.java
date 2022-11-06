package com.ivarrace.gringotts.infrastructure.db.springdata.repository;

import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataGroupRepository extends JpaRepository<GroupEntity, UUID> {

    List<GroupEntity> findAllByTypeAndAccountancy_key(String type, String accountancyKey); //NOSONAR

    Optional<GroupEntity> findByKeyAndTypeAndAccountancy_key(String groupKey, String type, String accountancyId); //NOSONAR
}
