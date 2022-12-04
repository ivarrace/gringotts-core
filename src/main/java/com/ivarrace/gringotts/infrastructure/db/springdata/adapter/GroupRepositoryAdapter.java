package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.application.ports.data.GroupRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.GroupEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.GroupEntityMapper;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataGroupRepository;
import com.ivarrace.gringotts.infrastructure.db.springdata.utils.ExampleGenerator;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupRepositoryAdapter implements GroupRepositoryPort {

    private final SpringDataGroupRepository repository;

    public GroupRepositoryAdapter(SpringDataGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Group> findAll(User currentUser, String accountancyKey, GroupType groupType) {
        Example<GroupEntity> example = ExampleGenerator.getGroupExample(currentUser, Optional.of(accountancyKey), Optional.of(groupType), Optional.empty());
        return GroupEntityMapper.toDomainList(repository.findAll(example));
    }

    @Override
    public Optional<Group> findOne(User currentUser,
                                   String accountancyKey,
                                   GroupType groupType,
                                   String groupKey) {
        Example<GroupEntity> example = ExampleGenerator.getGroupExample(currentUser, Optional.of(accountancyKey), Optional.of(groupType), Optional.of(groupKey));
        return GroupEntityMapper.toDomain(repository.findOne(example));
    }

    @Override
    public Group save(Group group) {
        GroupEntity newGroup = GroupEntityMapper.toDbo(group);
        return GroupEntityMapper.toDomain(repository.save(newGroup));
    }

    @Override
    public void delete(Group group) {
        repository.delete(GroupEntityMapper.toDbo(group));
    }

}
