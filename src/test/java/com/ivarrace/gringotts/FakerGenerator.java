package com.ivarrace.gringotts;

import com.github.javafaker.Faker;
import com.ivarrace.gringotts.domain.accountancy.*;
import com.ivarrace.gringotts.domain.user.UserAuthority;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class FakerGenerator {

    private static final Faker faker = new Faker();

    public static AccountancyEntity fakerAccountancyEntity() {
        AccountancyEntity entity = new AccountancyEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(faker.name().name());
        entity.setKey(faker.regexify("[a-z]{10}[_][a-z]{10}"));
        entity.setCreatedDate(LocalDateTime.ofInstant(faker.date().past(42, 10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setLastModified(LocalDateTime.ofInstant(faker.date().past(10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setGroups(Collections.emptyList());
        entity.setUsers(Collections.emptyList());
        return entity;
    }

    public static Accountancy fakerAccountancy() {
        Accountancy entity = new Accountancy();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(faker.name().name());
        entity.setKey(faker.regexify("[a-z]{10}[_][a-z]{10}"));
        entity.setCreatedDate(LocalDateTime.ofInstant(faker.date().past(42, 10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setLastModified(LocalDateTime.ofInstant(faker.date().past(10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setIncomes(Collections.emptyList());
        entity.setExpenses(Collections.emptyList());
        entity.setUsers(Collections.emptyList());
        return entity;
    }

    public static GroupEntity fakerGroupEntity() {
        GroupEntity entity = new GroupEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(faker.name().name());
        entity.setKey(faker.regexify("[a-z]{10}[_][a-z]{10}"));
        entity.setCreatedDate(LocalDateTime.ofInstant(faker.date().past(42, 10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setLastModified(LocalDateTime.ofInstant(faker.date().past(10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setType(optionFromEnum(GroupType.class).name());
        entity.setAccountancy(fakerAccountancyEntity());
        entity.setCategories(Collections.emptyList());
        return entity;
    }

    public static Group fakerGroup() {
        Group entity = new Group();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(faker.name().name());
        entity.setKey(faker.regexify("[a-z]{10}[_][a-z]{10}"));
        entity.setCreatedDate(LocalDateTime.ofInstant(faker.date().past(42, 10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setLastModified(LocalDateTime.ofInstant(faker.date().past(10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setType(optionFromEnum(GroupType.class));
        entity.setAccountancy(fakerAccountancy());
        entity.setCategories(Collections.emptyList());
        return entity;
    }

    public static CategoryEntity fakerCategoryEntity() {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(faker.name().name());
        entity.setKey(faker.regexify("[a-z]{10}[_][a-z]{10}"));
        entity.setCreatedDate(LocalDateTime.ofInstant(faker.date().past(42, 10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setLastModified(LocalDateTime.ofInstant(faker.date().past(10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setGroup(fakerGroupEntity());
        entity.setMovements(Collections.emptyList());
        return entity;
    }

    public static Category fakerCategory() {
        Category entity = new Category();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(faker.name().name());
        entity.setKey(faker.regexify("[a-z]{10}[_][a-z]{10}"));
        entity.setCreatedDate(LocalDateTime.ofInstant(faker.date().past(42, 10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setLastModified(LocalDateTime.ofInstant(faker.date().past(10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setGroup(fakerGroup());
        return entity;
    }

    public static MovementEntity fakerMovementEntity() {
        MovementEntity entity = new MovementEntity();
        entity.setId(UUID.randomUUID());
        entity.setDate(LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()));
        entity.setAmount(new BigDecimal(faker.number().randomDouble(2,0, 1000)));
        entity.setInfo(faker.harryPotter().spell());
        entity.setCategory(fakerCategoryEntity());
        return entity;
    }

    public static Movement fakerMovement() {
        Movement entity = new Movement();
        entity.setId(UUID.randomUUID().toString());
        entity.setDate(LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()));
        entity.setAmount(new BigDecimal(faker.number().randomDouble(2,0, 1000)));
        entity.setInfo(faker.harryPotter().spell());
        entity.setCategory(fakerCategory());
        return entity;
    }

    public static UserEntity fakerUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setCreatedDate(LocalDateTime.ofInstant(faker.date().past(42, 10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setLastModified(LocalDateTime.ofInstant(faker.date().past(10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
        entity.setAuthority(UserAuthority.USER.name());
        entity.setPassword(faker.crypto().sha256());
        entity.setUsername(faker.name().username());
        entity.setNonExpired(true);
        entity.setNonLocked(true);
        entity.setCredentialNonExpired(true);
        entity.setEnabled(true);
        return entity;
    }

    private static <E extends Enum<E>> E optionFromEnum(Class<E> enumeration) {
        E[] enumConstants = enumeration.getEnumConstants();
        return enumConstants[faker.random().nextInt(enumConstants.length)];
    }
}
