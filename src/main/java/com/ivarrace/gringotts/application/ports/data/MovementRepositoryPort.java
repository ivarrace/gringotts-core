package com.ivarrace.gringotts.application.ports.data;

import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.domain.user.User;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface MovementRepositoryPort {

    List<Movement> findAll(String accountancyKey, Optional<GroupType> groupType, Optional<String> groupKey,
                           Optional<String> categoryKey,
                           Optional<Month> month, Optional<Year> year, User currentUser);

    Optional<Movement> findById(String movementId);

    Movement save(Movement movement);

    void delete(Movement existing);
}
