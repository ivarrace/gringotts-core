package com.ivarrace.gringotts.infrastructure.db.springdata.mapper;

import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRole;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.AccountancyEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.AccountancyUserEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountancyUserEntityMapper {

    public static List<AccountancyUserEntity> toDbo(List<AccountancyUserRole> users, AccountancyEntity accountancyEntity) {
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }
        List<AccountancyUserEntity> result = new ArrayList<>();
        for (AccountancyUserRole accountancyUserRole : users) {
            AccountancyUserEntity entity = new AccountancyUserEntity();
            entity.setId(accountancyUserRole.getId() != null ?
                    UUID.fromString(accountancyUserRole.getId()) : null);
            entity.setAccountancy(accountancyEntity);
            if(accountancyUserRole.getUser()!=null){
                UserEntity usr = new UserEntity();
                usr.setId(accountancyUserRole.getUser().getId() != null ?
                        UUID.fromString(accountancyUserRole.getUser().getId()) : null);
                entity.setUser(usr);
            }
            entity.setScope(accountancyUserRole.getRole()!=null ? accountancyUserRole.getRole().name() : null);
            result.add(entity);
        }
        return result;
    }

    public static List<AccountancyUserRole> toDomain(List<AccountancyUserEntity> users) {
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }
        List<AccountancyUserRole> result = new ArrayList<>();
        for (AccountancyUserEntity accountancyUserEntity : users) {
            AccountancyUserRole accountancyUserRole = new AccountancyUserRole();
            accountancyUserRole.setId(accountancyUserEntity.getId().toString());
            accountancyUserRole.setUser(UserEntityMapper.toDomain(accountancyUserEntity.getUser()));
            accountancyUserRole.setRole(AccountancyUserRoleType.valueOf(accountancyUserEntity.getScope()));
            result.add(accountancyUserRole);
        }
        return result;
    }

}
