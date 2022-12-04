package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.application.ports.data.MovementRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.*;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.MovementEntityMapper;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.Utils;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataMovementRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.YearMonth;
import java.util.*;

@Service
public class MovementRepositoryAdapter implements MovementRepositoryPort {

    private final SpringDataMovementRepository springDataMovementRepository;

    public MovementRepositoryAdapter(SpringDataMovementRepository springDataMovementRepository) {
        this.springDataMovementRepository = springDataMovementRepository;
    }

    @Override
    public List<Movement> findAll(Optional<String> accountancyKey, Optional<GroupType> groupType, Optional<String> groupKey, Optional<String> categoryKey,
                                  Optional<Integer> monthOrdinal, Optional<Integer> year, User currentUser) {
        MovementEntity exampleMovement = new MovementEntity();
        CategoryEntity exampleCategory = new CategoryEntity();
        GroupEntity exampleGroup = new GroupEntity();
        AccountancyEntity exampleAccountancy = new AccountancyEntity();
        AccountancyUserEntity accountancyUser = new AccountancyUserEntity();
        UserEntity user = new UserEntity();
        user.setId(UUID.fromString(currentUser.getId()));
        accountancyUser.setUser(user);
        exampleAccountancy.setUsers(Collections.singletonList(accountancyUser));
        exampleGroup.setAccountancy(exampleAccountancy);

        exampleCategory.setKey(categoryKey.orElse(null));
        exampleMovement.setCategory(exampleCategory);

        exampleGroup.setKey(groupKey.orElse(null));
        exampleGroup.setType(groupType.isPresent() ? groupType.get().name() : null);
        exampleCategory.setGroup(exampleGroup);

        exampleAccountancy.setKey(accountancyKey.orElse(null));

        Example<MovementEntity> example = Example.of(exampleMovement);
        List<MovementEntity> result; //TODO filter in query by dates
        if(!year.isPresent()){
            if(!monthOrdinal.isPresent()){
                result = Streamable.of(springDataMovementRepository.findAll(example)).toList();
            } else {
                result = Streamable.of(springDataMovementRepository.findAll(example))
                        .filter(movementEntity -> movementEntity.getDate().getMonth().equals(Month.of(monthOrdinal.get())))
                        .toList();
            }
        } else {
            if(!monthOrdinal.isPresent()){
                result = Streamable.of(springDataMovementRepository.findAll(example))
                        .filter(movementEntity -> movementEntity.getDate().getYear()==year.get())
                        .toList();
            } else {
                YearMonth findInDate = YearMonth.of(year.get(), Month.of(monthOrdinal.get()));
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
