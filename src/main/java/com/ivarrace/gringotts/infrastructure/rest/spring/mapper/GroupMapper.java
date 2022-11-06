package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.Utils;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.GroupResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.NewGroupCommand;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    private GroupMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static GroupResponse toResponse(Group group) {
        if (group == null) {
            return null;
        }
        GroupResponse response = new GroupResponse();
        response.setId(group.getId());
        response.setKey(group.getKey());
        response.setName(group.getName());
        response.setCreatedDate(group.getCreatedDate());
        response.setCategories(CategoryMapper.toResponse(group.getCategories()));
        return response;
    }

    public static List<GroupResponse> toResponse(List<Group> groups) {
        if (groups == null || groups.isEmpty()) {
            return Collections.emptyList();
        }
        return groups.stream().map(GroupMapper::toResponse).collect(Collectors.toList());
    }

    public static Group toDomain(NewGroupCommand command, GroupType type,
                                 String accountancyKey) {
        if (command == null) {
            return null;
        }
        Group group = new Group();
        group.setName(command.getName());
        group.setType(type);
        Accountancy accountancy = new Accountancy();
        accountancy.setKey(accountancyKey);
        group.setAccountancy(accountancy);
        group.setKey(Utils.nameToKey(command.getName()));
        return group;
    }

}