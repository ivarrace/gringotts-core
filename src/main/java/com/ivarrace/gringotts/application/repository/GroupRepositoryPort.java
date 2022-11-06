package com.ivarrace.gringotts.application.repository;

import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;

import java.util.List;
import java.util.Optional;

public interface GroupRepositoryPort {

    List<Group> findAllByTypeInAccountancy(GroupType type, String accountancyKey);

    Optional<Group> findByKeyAndTypeInAccountancy(String groupKey,
                                                    GroupType groupType,
                                                    String accountancyKey);

    Group save(Group group);

    void delete(Group group);
}
