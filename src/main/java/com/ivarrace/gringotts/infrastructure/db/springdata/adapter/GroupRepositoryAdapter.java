package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.application.repository.GroupRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.GroupEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.GroupEntityMapper;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataGroupRepository;
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
    public List<Group> findAllByTypeInAccountancy(GroupType type, String accountancyKey) {
        return GroupEntityMapper.toDomainList(repository.findAllByTypeAndAccountancy_key(type.name(), accountancyKey));
    }

    @Override
    public Optional<Group> findByKeyAndTypeInAccountancy(String groupKey,
                                                           GroupType groupType,
                                                           String accountancyKey) {
        return GroupEntityMapper.toDomain(repository.findByKeyAndTypeAndAccountancy_key(groupKey, groupType.name(), accountancyKey));
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
