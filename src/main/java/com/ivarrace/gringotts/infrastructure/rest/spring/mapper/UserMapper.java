package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.NewUserCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.UserResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy =
        InjectionStrategy.CONSTRUCTOR, uses = {UtilsMapper.class,
        GroupMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", source = "user.id", qualifiedByName ="stringToUUID")
    UserResponse toResponse(User user);

    @Mapping(target = "authority", constant = "USER")
    User toDomain(NewUserCommand newUser);
}