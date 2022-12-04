package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.application.ports.data.MovementRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.MovementEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.MovementEntityMapper;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.Utils;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataMovementRepository;
import com.ivarrace.gringotts.infrastructure.db.springdata.utils.ExampleGenerator;
import org.springframework.data.domain.Example;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class MovementRepositoryAdapter implements MovementRepositoryPort {

    private final SpringDataMovementRepository springDataMovementRepository;

    public MovementRepositoryAdapter(SpringDataMovementRepository springDataMovementRepository) {
        this.springDataMovementRepository = springDataMovementRepository;
    }

    @Override
    public List<Movement> findAll(Optional<String> accountancyKey, Optional<GroupType> groupType,
                                  Optional<String> groupKey, Optional<String> categoryKey,
                                  Optional<Month> month, Optional<Year> year, User currentUser) {
        Example<MovementEntity> example = ExampleGenerator.getMovementExample(currentUser, accountancyKey, groupType,
                groupKey, categoryKey, Optional.empty());
        List<MovementEntity> result; //TODO filter in query by dates
        if (!year.isPresent()) {
            if (!month.isPresent()) {
                result = Streamable.of(springDataMovementRepository.findAll(example)).toList();
            } else {
                result = Streamable.of(springDataMovementRepository.findAll(example))
                        .filter(movementEntity -> movementEntity.getDate().getMonth().equals(month.get()))
                        .toList();
            }
        } else {
            if (!month.isPresent()) {
                result = Streamable.of(springDataMovementRepository.findAll(example))
                        .filter(movementEntity -> movementEntity.getDate().getYear() == year.get().getValue())
                        .toList();
            } else {
                YearMonth findInDate = YearMonth.of(year.get().getValue(), month.get());
                result = Streamable.of(springDataMovementRepository.findAll(example))
                        .filter(movementEntity -> YearMonth.from(movementEntity.getDate()).equals(findInDate))
                        .toList();
            }
        }

        return MovementEntityMapper.toDomainList(result);

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
