package com.ivarrace.gringotts.infrastructure.db.springdata.mapper;

import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRole;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.GroupEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupEntityMapper {

    public static Group toDomain(GroupEntity entity) {
        if (entity == null) {
            return null;
        }
        Group domain = new Group();
        domain.setId(entity.getId().toString());
        domain.setName(entity.getName());
        domain.setCreatedDate(entity.getCreatedDate());
        domain.setLastModified(entity.getLastModified());
        domain.setCategories(CategoryEntityMapper.toDomainList(entity.getCategories()));
        domain.setType(GroupType.valueOf(entity.getType()));
        domain.setKey(entity.getKey());
        Accountancy accountancy = new Accountancy();
        accountancy.setId(entity.getAccountancy().getId().toString());
        accountancy.setKey(entity.getAccountancy().getKey());List<AccountancyUserRole> users = entity.getAccountancy().getUsers().stream().map(accountancyUserEntity -> {
            AccountancyUserRole userRole = new AccountancyUserRole();
            userRole.setId(accountancyUserEntity.getId().toString());
            return userRole;
        }).collect(Collectors.toList());
        accountancy.setUsers(users);
        domain.setAccountancy(accountancy);
        return domain;
    }

    public static List<Group> toDomainList(List<GroupEntity> entityList) {
        if(entityList == null){
            return Collections.emptyList();
        }
        return entityList.stream().map(GroupEntityMapper::toDomain).collect(Collectors.toList());
    }

    public static GroupEntity toDbo(Group domain) {
        if (domain == null) {
            return null;
        }
        GroupEntity entity = new GroupEntity();
        entity.setId(domain.getId() != null ? UUID.fromString(domain.getId()) : null);
        entity.setCreatedDate(domain.getCreatedDate());
        entity.setLastModified(domain.getLastModified());
        entity.setName(domain.getName());
        entity.setType(domain.getType().name());
        entity.setAccountancy(AccountancyEntityMapper.toDbo(domain.getAccountancy()));
        entity.setKey(domain.getKey());
        return entity;
    }

    public static Optional<Group> toDomain(Optional<GroupEntity> entity) {
        if (!entity.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(toDomain(entity.get()));
    }

}
