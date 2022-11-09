package com.ivarrace.gringotts.application.repository;

import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface MovementRepositoryPort {

    List<Movement> findAllByCategory(String categoryKey, User currentUser);

    List<Movement> findAllByGroup(String groupKey, GroupType groupType, User currentUser);

    List<Movement> findAllByGroupType(GroupType groupType, User currentUser);

    List<Movement> findAllByAccountancy(String accountancyKey, User currentUser);

    List<Movement> findAllByUser(User currentUser);

    Optional<Movement> findById(String movementId);

    Movement save(Movement movement);

    void delete(Movement existing);
}
