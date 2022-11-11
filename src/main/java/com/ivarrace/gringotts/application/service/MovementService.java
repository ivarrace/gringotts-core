package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.ports.data.MovementRepositoryPort;
import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;
import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.domain.exception.InvalidParameterException;
import com.ivarrace.gringotts.domain.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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

    public List<Movement> findAll(String accountancyKey, String groupKey, GroupType groupType, String categoryKey) {
        if (categoryKey != null) {
            return movementRepositoryPort.findAllByCategory(categoryKey, authPort.getCurrentUser());
        } else if (groupKey != null) {
            if (groupType == null) {
                throw new InvalidParameterException("groupType", "empty");
            }
            return movementRepositoryPort.findAllByGroup(groupKey, groupType, authPort.getCurrentUser());
        } else if (groupType != null) {
            return movementRepositoryPort.findAllByGroupType(groupType, authPort.getCurrentUser());
        } else if (accountancyKey != null) {
            return movementRepositoryPort.findAllByAccountancy(accountancyKey, authPort.getCurrentUser());
        } else {
            return movementRepositoryPort.findAllByUser(authPort.getCurrentUser());
        }
    }

    public Movement findById(String movementId) {
        Movement movement =
                movementRepositoryPort.findById(movementId).orElseThrow(() -> new ObjectNotFoundException(movementId));
        accountancyUserRoleChecker.validatePermission(movement.getCategory().getGroup().getAccountancy().getKey(),
                AccountancyUserRoleType.VIEWER);
        return movement;
    }

    public Movement create(Movement movement) {
        accountancyUserRoleChecker.validatePermission(movement.getCategory().getGroup().getAccountancy().getKey(),
                AccountancyUserRoleType.EDITOR);
        Category category = categoryService.findByKeyInGroup(movement.getCategory().getKey(),
                movement.getCategory().getGroup().getKey(), movement.getCategory().getGroup().getType(),
                movement.getCategory().getGroup().getAccountancy().getKey());
        movement.setCategory(category);
        return movementRepositoryPort.save(movement);

    }

    public Movement modify(String movementId, Movement movement) throws ObjectNotFoundException {
        Movement existing = this.findById(movementId);
        accountancyUserRoleChecker.validatePermission(existing.getCategory().getGroup().getAccountancy().getKey(),
                AccountancyUserRoleType.EDITOR);
        movement.setId(existing.getId());
        movement.setCategory(existing.getCategory());
        return movementRepositoryPort.save(movement);
    }

    public void delete(String movementId) throws ObjectNotFoundException {
        Movement existing = this.findById(movementId);
        accountancyUserRoleChecker.validatePermission(existing.getCategory().getGroup().getAccountancy().getKey(),
                AccountancyUserRoleType.EDITOR);
        movementRepositoryPort.delete(existing);
    }
}
