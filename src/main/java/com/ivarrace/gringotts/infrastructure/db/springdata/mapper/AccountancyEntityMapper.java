package com.ivarrace.gringotts.infrastructure.db.springdata.mapper;

import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRole;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.AccountancyEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.AccountancyUserEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountancyEntityMapper {

    public static Accountancy toDomain(AccountancyEntity entity) {
        Accountancy domain = new Accountancy();
        domain.setId(entity.getId().toString());
        domain.setCreatedDate(entity.getCreatedDate());
        domain.setLastModified(entity.getLastModified());
        domain.setName(entity.getName());
        domain.setKey(entity.getKey());
        if (entity.getGroups() == null) {
            domain.setIncomes(Collections.emptyList());
            domain.setExpenses(Collections.emptyList());
        } else {
            domain.setIncomes(
                    entity.getGroups().stream()
                            .filter(group -> GroupType.INCOMES.name().equals(group.getType()))
                            .map(GroupEntityMapper::toDomain)
                            .collect(Collectors.toList()));
            domain.setExpenses(
                    entity.getGroups().stream()
                            .filter(group -> GroupType.EXPENSES.name().equals(group.getType()))
                            .map(GroupEntityMapper::toDomain)
                            .collect(Collectors.toList()));
        }
        domain.setUsers(mapUsersToDomain(entity.getUsers()));
        return domain;
    }

    public static Optional<Accountancy> toDomain(Optional<AccountancyEntity> entity) {
        if (!entity.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(toDomain(entity.get()));
    }

    public static List<Accountancy> toDomain(List<AccountancyEntity> entityList) {
        return entityList.stream().map(AccountancyEntityMapper::toDomain).collect(Collectors.toList());
    }

    public static AccountancyEntity toDbo(Accountancy domain) {
        if (domain == null) {
            return null;
        }
        AccountancyEntity entity = new AccountancyEntity();
        entity.setId(domain.getId() != null ?
                UUID.fromString(domain.getId()) : null);
        entity.setCreatedDate(domain.getCreatedDate());
        entity.setLastModified(domain.getLastModified());
        entity.setKey(domain.getKey());
        entity.setName(domain.getName());
        entity.setGroups(Collections.emptyList());
        entity.setUsers(mapUsersToDbo(domain.getUsers(), entity));
        return entity;
    }

    private static List<AccountancyUserEntity> mapUsersToDbo(List<AccountancyUserRole> users, AccountancyEntity accountancyEntity) {
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }
        List<AccountancyUserEntity> result = new ArrayList<>();
        for (AccountancyUserRole accountancyUserRole : users) {
            AccountancyUserEntity entity = new AccountancyUserEntity();
            entity.setAccountancy(accountancyEntity);
            UserEntity usr = new UserEntity();
            usr.setId(Utils.toUUID(accountancyUserRole.getUser().getId()));
            entity.setUser(usr);
            entity.setScope(accountancyUserRole.getRole().name());
            result.add(entity);
        }
        return result;
    }

    private static List<AccountancyUserRole> mapUsersToDomain(List<AccountancyUserEntity> users) {
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }
        List<AccountancyUserRole> result = new ArrayList<>();
        for (AccountancyUserEntity accountancyUserEntity : users) {
            AccountancyUserRole accountancyUserRole = new AccountancyUserRole();
            accountancyUserRole.setUser(UserEntityMapper.toDomain(accountancyUserEntity.getUser()));
            accountancyUserRole.setRole(AccountancyUserRoleType.valueOf(accountancyUserEntity.getScope()));
            result.add(accountancyUserRole);
        }
        return result;
    }

}
