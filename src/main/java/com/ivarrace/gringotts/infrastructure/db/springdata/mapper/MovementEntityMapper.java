package com.ivarrace.gringotts.infrastructure.db.springdata.mapper;

import com.ivarrace.gringotts.domain.accountancy.*;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.MovementEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MovementEntityMapper {

    public static Movement toDomain(MovementEntity entity) {
        if (entity == null) {
            return null;
        }
        Movement domain = new Movement();
        domain.setId(entity.getId().toString());
        domain.setAmount(entity.getAmount());
        domain.setDate(entity.getDate());
        domain.setInfo(entity.getInfo());
        Category category = new Category();
        category.setId(entity.getCategory().getId().toString());
        category.setKey(entity.getCategory().getKey());
        Group group = new Group();
        group.setId(entity.getCategory().getGroup().getId().toString());
        group.setKey(entity.getCategory().getGroup().getKey());
        group.setType(GroupType.valueOf(entity.getCategory().getGroup().getType()));
        Accountancy accountancy = new Accountancy();
        accountancy.setId(entity.getCategory().getGroup().getAccountancy().getId().toString());
        accountancy.setKey(entity.getCategory().getGroup().getAccountancy().getKey());
        group.setAccountancy(accountancy);
        group.setAccountancy(accountancy);
        category.setGroup(group);
        domain.setCategory(category);
        return domain;
    }

    public static List<Movement> toDomainList(List<MovementEntity> entityList) {
        return entityList.stream().map(MovementEntityMapper::toDomain).collect(Collectors.toList());
    }

    public static MovementEntity toDbo(Movement domain) {
        if (domain == null) {
            return null;
        }
        MovementEntity entity = new MovementEntity();
        entity.setId(domain.getId() != null ?
                UUID.fromString(domain.getId()) : null);
        entity.setAmount(domain.getAmount());
        entity.setDate(domain.getDate());
        entity.setInfo(domain.getInfo());
        entity.setCategory(CategoryEntityMapper.toDbo(domain.getCategory()));
        return entity;
    }

    public static Optional<Movement> toDomain(Optional<MovementEntity> entity) {
        return entity.isPresent() ? Optional.of(toDomain(entity.get())) : Optional.empty();
    }
}
