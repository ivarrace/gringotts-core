package com.ivarrace.gringotts.infrastructure.db.springdata.repository;

import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataMovementRepository extends JpaRepository<MovementEntity, UUID> {

    List<MovementEntity> findAllByCategory_keyAndCategory_Group_Accountancy_Users_UserId(String categoryKey, UUID userId); //NOSONAR

    List<MovementEntity> findAllByCategory_Group_keyAndCategory_Group_typeAndCategory_Group_Accountancy_Users_UserId(String groupKey, String groupType, UUID userId); //NOSONAR

    List<MovementEntity> findAllByCategory_Group_typeAndCategory_Group_Accountancy_Users_UserId(String groupType, UUID userId); //NOSONAR

    List<MovementEntity> findAllByCategory_Group_Accountancy_keyAndCategory_Group_Accountancy_Users_UserId(String accountancyKey, UUID userId); //NOSONAR

    //List<MovementEntity> findAllByUser_id(UUID userId); //NOSONAR

    List<MovementEntity> findAllByCategory_Group_Accountancy_Users_UserId(UUID userId); //NOSONAR
}
