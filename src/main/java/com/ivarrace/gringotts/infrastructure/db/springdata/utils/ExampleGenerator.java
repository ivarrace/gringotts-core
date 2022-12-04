package com.ivarrace.gringotts.infrastructure.db.springdata.utils;

import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Example;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExampleGenerator {

    public static Example<AccountancyEntity> getAccountancyExample(User currentUser,
                                                                   Optional<String> accountancyKey) {
        AccountancyEntity accountancyEntity = new AccountancyEntity();
        accountancyEntity.setKey(accountancyKey.orElse(null));
        AccountancyUserEntity accountancyUser = new AccountancyUserEntity();
        UserEntity user = new UserEntity();
        user.setId(UUID.fromString(currentUser.getId()));
        accountancyUser.setUser(user);
        accountancyEntity.setUsers(Collections.singletonList(accountancyUser));
        return Example.of(accountancyEntity);
    }

    public static Example<GroupEntity> getGroupExample(User currentUser, Optional<String> accountancyKey,
                                                        Optional<GroupType> groupType,
                                                        Optional<String> groupKey) {
        GroupEntity entity = new GroupEntity();
        entity.setKey(groupKey.orElse(null));
        entity.setType(groupType.isPresent() ? groupType.get().name() : null);

        Example<AccountancyEntity> accountancyEntityExample = getAccountancyExample(currentUser, accountancyKey);
        entity.setAccountancy(accountancyEntityExample.getProbe());
        return Example.of(entity);
    }

    public static Example<CategoryEntity> getCategoryExample(User currentUser, Optional<String> accountancyKey,
                                                             Optional<GroupType> groupType,
                                                             Optional<String> groupKey,
                                                             Optional<String> categoryKey) {
        CategoryEntity entity = new CategoryEntity();
        entity.setKey(categoryKey.orElse(null));

        Example<GroupEntity> groupEntityExample = getGroupExample(currentUser, accountancyKey, groupType, groupKey);
        entity.setGroup(groupEntityExample.getProbe());
        return Example.of(entity);
    }

    public static Example<MovementEntity> getMovementExample(User currentUser, Optional<String> accountancyKey,
                                                             Optional<GroupType> groupType,
                                                             Optional<String> groupKey,
                                                             Optional<String> categoryKey,
                                                             Optional<String> movementId) {
        MovementEntity entity = new MovementEntity();
        entity.setId(movementId.isPresent() ? UUID.fromString(movementId.get()) : null);

        Example<CategoryEntity> categoryEntityExample = getCategoryExample(currentUser, accountancyKey, groupType, groupKey, categoryKey);
        entity.setCategory(categoryEntityExample.getProbe());
        return Example.of(entity);
    }


}
