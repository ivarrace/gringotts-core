package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.MovementResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.NewMovementCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.UpdateMovementCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy =
        InjectionStrategy.CONSTRUCTOR, uses = {UtilsMapper.class,
        GroupMapper.class})
public interface MovementMapper {

    MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);

    MovementResponse toResponse(Movement movement);

    List<MovementResponse> toResponse(List<Movement> accountancyList);

    @Mapping(target = "category.key", source = "command.categoryKey")
    @Mapping(target = "category.group.key", source = "command.groupKey")
    @Mapping(target = "category.group.type", source = "command.groupType")
    @Mapping(target = "category.group.accountancy.key", source = "command.accountancyKey")
    Movement toDomain(NewMovementCommand command);

    Movement toDomain(UpdateMovementCommand command);
}