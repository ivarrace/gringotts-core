package com.ivarrace.gringotts.infrastructure.db.springdata.mapper;

import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.CategoryEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryEntityMapper {

    public static Category toDomain(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        Category domain = new Category();
        domain.setId(entity.getId().toString());
        domain.setName(entity.getName());
        domain.setCreatedDate(entity.getCreatedDate());
        domain.setLastModified(entity.getLastModified());
        domain.setKey(entity.getKey());
        Group group = new Group();
        group.setId(entity.getGroup().getId().toString());
        group.setKey(entity.getGroup().getKey());
        group.setType(GroupType.valueOf(entity.getGroup().getType()));
        Accountancy accountancy = new Accountancy();
        accountancy.setId(entity.getGroup().getAccountancy().getId().toString());
        accountancy.setKey(entity.getGroup().getAccountancy().getKey());
        group.setAccountancy(accountancy);
        domain.setGroup(group);
        return domain;
    }

    public static List<Category> toDomainList(List<CategoryEntity> entityList) {
        if(entityList == null){
            return Collections.emptyList();
        }
        return entityList.stream().map(CategoryEntityMapper::toDomain).collect(Collectors.toList());
    }

    public static CategoryEntity toDbo(Category domain) {
        if (domain == null) {
            return null;
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setId(domain.getId() != null ? UUID.fromString(domain.getId()) : null);
        entity.setCreatedDate(domain.getCreatedDate());
        entity.setLastModified(domain.getLastModified());
        entity.setName(domain.getName());
        entity.setKey(domain.getKey());
        entity.setGroup(GroupEntityMapper.toDbo(domain.getGroup()));
        return entity;
    }

    public static Optional<Category> toDomain(Optional<CategoryEntity> entity) {
        if (!entity.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(toDomain(entity.get()));
    }
}
