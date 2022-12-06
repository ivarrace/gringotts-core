package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.ports.data.MovementRepositoryPort;
import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.domain.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MovementService {

    private final AuthPort authPort;
    private final CategoryService categoryService;
    private final MovementRepositoryPort movementRepositoryPort;

    public MovementService(AuthPort authPort,
                           CategoryService categoryService,
                           MovementRepositoryPort movementRepositoryPort) {
        this.movementRepositoryPort = movementRepositoryPort;
        this.categoryService = categoryService;
        this.authPort = authPort;
    }

    public List<Movement> findAll(String accountancyKey, Optional<String> groupKey,
                                  Optional<GroupType> groupType, Optional<String> categoryKey,
                                  Optional<Month> month, Optional<Year> year) {
        return movementRepositoryPort.findAll(
                accountancyKey, groupType, groupKey, categoryKey, month, year, authPort.getCurrentUser());
    }

    public Movement findOne(String movementId) {
        return movementRepositoryPort.findById(movementId).orElseThrow(() -> new ObjectNotFoundException(movementId));
    }

    public Movement create(String accountancyKey, Movement movement) {
        Category category = categoryService.findOne(
                accountancyKey,
                movement.getCategory().getGroup().getType(),
                movement.getCategory().getGroup().getKey(),
                movement.getCategory().getKey());
        movement.setCategory(category);
        return movementRepositoryPort.save(movement);

    }

    public Movement modify(String movementId, Movement movement) throws ObjectNotFoundException {
        Movement existing = this.findOne(movementId);
        movement.setId(existing.getId());
        movement.setCategory(existing.getCategory());
        return movementRepositoryPort.save(movement);
    }

    public void delete(String movementId) throws ObjectNotFoundException {
        Movement existing = this.findOne(movementId);
        movementRepositoryPort.delete(existing);
    }
}
