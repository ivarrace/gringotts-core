package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.application.repository.MovementRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.MovementEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.MovementEntityMapper;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.Utils;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataMovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementRepositoryAdapter implements MovementRepositoryPort {

    private final SpringDataMovementRepository springDataMovementRepository;

    public MovementRepositoryAdapter(SpringDataMovementRepository springDataMovementRepository) {
        this.springDataMovementRepository = springDataMovementRepository;
    }

    @Override
    public List<Movement> findAllByCategory(String categoryKey) {
        return MovementEntityMapper.toDomainList(springDataMovementRepository.findAllByCategory_key(categoryKey));
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
