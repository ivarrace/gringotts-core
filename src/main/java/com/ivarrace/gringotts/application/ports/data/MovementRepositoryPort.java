package com.ivarrace.gringotts.application.ports.data;

import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface MovementRepositoryPort {

    List<Movement> findAll(String accountancyKey, GroupType groupType, String groupKey, String categoryKey,
                           Integer monthOrdinal, Integer year, User currentUser);

    Optional<Movement> findById(String movementId);

    Movement save(Movement movement);

    void delete(Movement existing);
}
