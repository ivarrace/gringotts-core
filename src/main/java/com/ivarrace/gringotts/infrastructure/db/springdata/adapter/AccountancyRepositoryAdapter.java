package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.application.ports.data.AccountancyRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.AccountancyEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.AccountancyEntityMapper;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataAccountancyRepository;
import com.ivarrace.gringotts.infrastructure.db.springdata.utils.ExampleGenerator;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountancyRepositoryAdapter implements AccountancyRepositoryPort {

    private final SpringDataAccountancyRepository accountancyRepository;

    public AccountancyRepositoryAdapter(SpringDataAccountancyRepository accountancyRepository) {
        this.accountancyRepository = accountancyRepository;
    }

    @Override
    public List<Accountancy> findAll(User currentUser) {
        Example<AccountancyEntity> example = ExampleGenerator.getAccountancyExample(currentUser, Optional.empty());
        return AccountancyEntityMapper.toDomain(accountancyRepository.findAll(example));
    }

    @Override
    public Optional<Accountancy> findOne(User currentUser, String accountancyKey) {
        Example<AccountancyEntity> example = ExampleGenerator.getAccountancyExample(currentUser, Optional.of(accountancyKey));
        return AccountancyEntityMapper.toDomain(accountancyRepository.findOne(example));
    }

    @Override
    public Accountancy save(Accountancy accountancy) {
        return AccountancyEntityMapper.toDomain(accountancyRepository.save(AccountancyEntityMapper.toDbo(accountancy)));
    }

    @Override
    public void delete(Accountancy accountancy) {
        accountancyRepository.delete(AccountancyEntityMapper.toDbo(accountancy));
    }

}
