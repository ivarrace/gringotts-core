package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.ports.data.GroupRepositoryPort;
import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.exception.ObjectAlreadyRegisteredException;
import com.ivarrace.gringotts.domain.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class GroupService {

    private final AuthPort authPort;
    private final AccountancyService accountancyService;
    private final GroupRepositoryPort groupRepositoryPort;

    public GroupService(AuthPort authPort, AccountancyService accountancyService, GroupRepositoryPort groupRepositoryPort) {
        this.authPort=authPort;
        this.groupRepositoryPort = groupRepositoryPort;
        this.accountancyService = accountancyService;
    }

    public List<Group> findAll(String accountancyKey, GroupType groupType) {
        return groupRepositoryPort.findAll(authPort.getCurrentUser(), accountancyKey, groupType);
    }

    public Group findOne(String groupKey, String accountancyKey, GroupType groupType) {
        return groupRepositoryPort.findOne(authPort.getCurrentUser(), accountancyKey, groupType, groupKey).orElseThrow(() -> new ObjectNotFoundException(groupKey));
    }

    public Group create(Group group) throws ObjectAlreadyRegisteredException {
        validateIfExists(group);
        Accountancy accountancy = accountancyService.findOne(group.getAccountancy().getKey());
        group.setAccountancy(accountancy);
        return groupRepositoryPort.save(group);
    }

    public Group modify(String groupKey, Group group) throws ObjectNotFoundException, ObjectAlreadyRegisteredException {
        validateIfExists(group);
        Group existing = this.findOne(groupKey, group.getAccountancy().getKey(), group.getType());
        group.setId(existing.getId());
        group.setAccountancy(existing.getAccountancy());
        return groupRepositoryPort.save(group);
    }

    public void delete(String accountancyKey, String groupKey, GroupType groupType) throws ObjectNotFoundException {
        Group existing = this.findOne(groupKey, accountancyKey, groupType);
        groupRepositoryPort.delete(existing);
    }

    private void validateIfExists(Group group) throws ObjectAlreadyRegisteredException{
        Optional<Group> updatedGroup = groupRepositoryPort.findOne(authPort.getCurrentUser(), group.getAccountancy().getKey(), group.getType(), group.getKey());
        if (updatedGroup.isPresent()) {
            throw new ObjectAlreadyRegisteredException(group.getKey());
        }
    }
}
