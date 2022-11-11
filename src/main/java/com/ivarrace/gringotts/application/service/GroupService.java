package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.ports.data.GroupRepositoryPort;
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

    private final GroupRepositoryPort groupRepositoryPort;
    private final AccountancyService accountancyService;

    public GroupService(GroupRepositoryPort groupRepositoryPort, AccountancyService accountancyService) {
        this.groupRepositoryPort = groupRepositoryPort;
        this.accountancyService = accountancyService;
    }

    public List<Group> findByAccountancyKeyAndType(String accountancyKey, GroupType groupType) {
        return groupRepositoryPort.findAllByTypeAndAccountancy(groupType, accountancyKey);
    }

    public Group create(Group group) {
        Optional<Group> existing = groupRepositoryPort.findByKeyAndTypeAndAccountancy(group.getKey(), group.getType()
                , group.getAccountancy().getKey());
        if (existing.isPresent()) {
            throw new ObjectAlreadyRegisteredException(group.getKey());
        }
        Accountancy accountancy = accountancyService.findByKey(group.getAccountancy().getKey());
        group.setAccountancy(accountancy);
        return groupRepositoryPort.save(group);
    }

    public Group findByKey(String groupKey, String accountancyKey, GroupType groupType) {
        return groupRepositoryPort.findByKeyAndTypeAndAccountancy(groupKey, groupType, accountancyKey).orElseThrow(() -> new ObjectNotFoundException(groupKey));
    }

    public Group modify(String groupKey, Group group) throws ObjectNotFoundException {
        Group existing = this.findByKey(groupKey, group.getAccountancy().getKey(), group.getType());
        group.setId(existing.getId());
        group.setAccountancy(existing.getAccountancy());
        return groupRepositoryPort.save(group);
    }

    public void delete(String accountancyKey, String groupKey, GroupType groupType) throws ObjectNotFoundException {
        Group existing = this.findByKey(groupKey, accountancyKey, groupType);
        groupRepositoryPort.delete(existing);
    }
}
