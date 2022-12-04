package com.ivarrace.gringotts.application.ports.data;

import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface GroupRepositoryPort {

    List<Group> findAll(User currentUser, String accountancyKey, GroupType groupType);

    Optional<Group> findOne(User currentUser, String accountancyKey, GroupType groupType, String groupKey);

    Group save(Group group);

    void delete(Group group);
}
