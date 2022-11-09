package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.domain.accountancy.*;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.MovementResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.NewMovementCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.UpdateMovementCommand;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MovementMapper {

    private MovementMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static MovementResponse toResponse(Movement movement) {
        if (movement == null) {
            return null;
        }
        MovementResponse response = new MovementResponse();
        response.setId(movement.getId());
        response.setDate(movement.getDate());
        response.setAmount(movement.getAmount());
        response.setInfo(movement.getInfo());
        return response;
    }

    public static List<MovementResponse> toResponse(List<Movement> accountancyList) {
        if (accountancyList == null || accountancyList.isEmpty()) {
            return Collections.emptyList();
        }
        return accountancyList.stream().map(MovementMapper::toResponse).collect(Collectors.toList());
    }

    public static Movement toDomain(NewMovementCommand command) {
        if (command == null) {
            return null;
        }
        Movement movement = new Movement();
        movement.setDate(command.getDate());
        movement.setAmount(command.getAmount());
        movement.setInfo(command.getInfo());
        Category category = new Category();
        category.setKey(command.getCategoryKey());
        Group group = new Group();
        group.setKey(command.getGroupKey());
        group.setType(command.getGroupType());
        Accountancy accountancy = new Accountancy();
        accountancy.setKey(command.getAccountancyKey());
        group.setAccountancy(accountancy);
        category.setGroup(group);
        movement.setCategory(category);
        return movement;
    }

    public static Movement toDomain(UpdateMovementCommand command) {
        if (command == null) {
            return null;
        }
        Movement movement = new Movement();
        movement.setDate(command.getDate());
        movement.setAmount(command.getAmount());
        movement.setInfo(command.getInfo());
        return movement;
    }
}