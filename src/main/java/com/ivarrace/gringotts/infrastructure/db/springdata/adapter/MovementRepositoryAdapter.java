package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.application.ports.data.MovementRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.MovementEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.MovementEntityMapper;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.Utils;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataMovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovementRepositoryAdapter implements MovementRepositoryPort {

    private final SpringDataMovementRepository springDataMovementRepository;

    public MovementRepositoryAdapter(SpringDataMovementRepository springDataMovementRepository) {
        this.springDataMovementRepository = springDataMovementRepository;
    }

    @Override
    public List<Movement> findAllByCategory(String categoryKey, User currentUser) {
        return MovementEntityMapper.toDomainList(springDataMovementRepository.findAllByCategory_keyAndCategory_Group_Accountancy_Users_UserId(categoryKey, UUID.fromString(currentUser.getId())));
    }

    @Override
    public List<Movement> findAllByGroup(String groupKey, GroupType groupType, User currentUser) {
        return MovementEntityMapper.toDomainList(springDataMovementRepository.findAllByCategory_Group_keyAndCategory_Group_typeAndCategory_Group_Accountancy_Users_UserId(groupKey, groupType.name(), UUID.fromString(currentUser.getId())));
    }

    @Override
    public List<Movement> findAllByGroupType(GroupType groupType, User currentUser) {
        return MovementEntityMapper.toDomainList(springDataMovementRepository.findAllByCategory_Group_typeAndCategory_Group_Accountancy_Users_UserId(groupType.name(), UUID.fromString(currentUser.getId())));
    }

    @Override
    public List<Movement> findAllByAccountancy(String accountancyKey, User currentUser) {
        return MovementEntityMapper.toDomainList(springDataMovementRepository.findAllByCategory_Group_Accountancy_keyAndCategory_Group_Accountancy_Users_UserId(accountancyKey, UUID.fromString(currentUser.getId())));
    }

    @Override
    public List<Movement> findAllByUser(User currentUser) {
        return MovementEntityMapper.toDomainList(springDataMovementRepository.findAllByCategory_Group_Accountancy_Users_UserId(UUID.fromString(currentUser.getId())));
    }

    @Override
    public Optional<Movement> findById(String movementId) {
        return MovementEntityMapper.toDomain(springDataMovementRepository.findById(Utils.toUUID(movementId)));
    }

    @Override
    public Movement save(Movement movement) {
        MovementEntity newMovement = MovementEntityMapper.toDbo(movement);
        return MovementEntityMapper.toDomain(springDataMovementRepository.save(newMovement));
    }

    @Override
    public void delete(Movement movement) {
        springDataMovementRepository.delete(MovementEntityMapper.toDbo(movement));
    }

}
