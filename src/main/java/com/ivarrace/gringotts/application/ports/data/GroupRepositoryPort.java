package com.ivarrace.gringotts.application.ports.data;

import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;

import java.util.List;
import java.util.Optional;

public interface GroupRepositoryPort {

    List<Group> findAllByTypeAndAccountancy(GroupType type, String accountancyKey);

    Optional<Group> findByKeyAndTypeAndAccountancy(String groupKey, GroupType groupType, String accountancyKey);

    Group save(Group group);

    void delete(Group group);
}
