package com.ivarrace.gringotts.application.repository;

import com.ivarrace.gringotts.domain.accountancy.Movement;

import java.util.List;
import java.util.Optional;

public interface MovementRepositoryPort {

    List<Movement> findAllByCategory(String categoryKey);

    Optional<Movement> findById(String movementId);

    Movement save(Movement movement);

    void delete(Movement existing);
}
