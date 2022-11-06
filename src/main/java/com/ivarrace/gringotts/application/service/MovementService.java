package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.exception.ObjectNotFoundException;
import com.ivarrace.gringotts.application.repository.MovementRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MovementService {

    private final MovementRepositoryPort movementRepositoryPort;

    private final CategoryService categoryService;

    public MovementService(MovementRepositoryPort movementRepositoryPort, CategoryService categoryService) {
        this.movementRepositoryPort = movementRepositoryPort;
        this.categoryService = categoryService;
    }

    public List<Movement> findAll(String categoryKey) {
        return movementRepositoryPort.findAllByCategory(categoryKey);
    }

    public Movement findById(String movementId){
        return movementRepositoryPort.findById(movementId)
                .orElseThrow(() -> new ObjectNotFoundException(movementId));
    }

    public Movement create(Movement movement) {
        Category category =
                categoryService.findByKeyInGroup(movement.getCategory().getKey(), movement.getCategory().getGroup().getKey());
        movement.setCategory(category);
        return movementRepositoryPort.save(movement);

    }

    public Movement modify(String movementId, Movement movement) throws ObjectNotFoundException {
        Movement existing = this.findById(movementId);
        movement.setId(existing.getId());
        movement.setCategory(existing.getCategory());
        return movementRepositoryPort.save(movement);
    }

    public void delete(String movementId) throws ObjectNotFoundException {
        Movement existing = this.findById(movementId);
        movementRepositoryPort.delete(existing);
    }
}
