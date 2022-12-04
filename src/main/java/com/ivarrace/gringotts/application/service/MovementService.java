package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.ports.data.MovementRepositoryPort;
import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;
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

    private final MovementRepositoryPort movementRepositoryPort;

    private final AuthPort authPort;

    private final AccountancyUserRoleChecker accountancyUserRoleChecker;

    private final CategoryService categoryService;

    public MovementService(MovementRepositoryPort movementRepositoryPort, CategoryService categoryService,
                           AuthPort authPort, AccountancyUserRoleChecker accountancyUserRoleChecker) {
        this.movementRepositoryPort = movementRepositoryPort;
        this.categoryService = categoryService;
        this.authPort = authPort;
        this.accountancyUserRoleChecker = accountancyUserRoleChecker;
    }

    public List<Movement> findAll(Optional<String> accountancyKey, Optional<String> groupKey,
                                  Optional<GroupType> groupType, Optional<String> categoryKey,
                                  Optional<Month> month, Optional<Year> year) {
        return movementRepositoryPort.findAll(
                accountancyKey, groupType, groupKey, categoryKey, month, year, authPort.getCurrentUser());
    }

    public Movement findOne(String movementId) {
        Movement movement =
                movementRepositoryPort.findById(movementId).orElseThrow(() -> new ObjectNotFoundException(movementId));
        accountancyUserRoleChecker.validatePermission(movement.getCategory().getGroup().getAccountancy().getKey(),
                AccountancyUserRoleType.VIEWER);
        return movement;
    }

    public Movement create(Movement movement) {
        accountancyUserRoleChecker.validatePermission(movement.getCategory().getGroup().getAccountancy().getKey(),
                AccountancyUserRoleType.EDITOR);
        Category category = categoryService.findOne(
                movement.getCategory().getGroup().getAccountancy().getKey(),
                movement.getCategory().getGroup().getType(),
                movement.getCategory().getGroup().getKey(),
                movement.getCategory().getKey());
        movement.setCategory(category);
        return movementRepositoryPort.save(movement);

    }

    public Movement modify(String movementId, Movement movement) throws ObjectNotFoundException {
        Movement existing = this.findOne(movementId);
        accountancyUserRoleChecker.validatePermission(existing.getCategory().getGroup().getAccountancy().getKey(),
                AccountancyUserRoleType.EDITOR);
        movement.setId(existing.getId());
        movement.setCategory(existing.getCategory());
        return movementRepositoryPort.save(movement);
    }

    public void delete(String movementId) throws ObjectNotFoundException {
        Movement existing = this.findOne(movementId);
        accountancyUserRoleChecker.validatePermission(existing.getCategory().getGroup().getAccountancy().getKey(),
                AccountancyUserRoleType.EDITOR);
        movementRepositoryPort.delete(existing);
    }
}
