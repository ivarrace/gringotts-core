package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.GroupResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.NewGroupCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UtilsMapper.class})
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    GroupResponse toResponse(Group group);

    List<GroupResponse> toResponse(List<Group> groups);

    @Mapping(target = "key", source = "command.name", qualifiedByName = "nameToKey")
    @Mapping(target = "type", source="groupType")
    @Mapping(target = "accountancy.key", source="accountancyKey")
    Group toDomain(NewGroupCommand command, GroupType groupType, String accountancyKey);

}