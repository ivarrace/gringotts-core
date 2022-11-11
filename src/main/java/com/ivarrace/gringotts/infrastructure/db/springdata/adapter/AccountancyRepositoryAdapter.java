package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.application.repository.AccountancyRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.AccountancyEntityMapper;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.Utils;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataAccountancyRepository;
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
    public List<Accountancy> findAllByUser(User currentUser) {
        return AccountancyEntityMapper.toDomain(accountancyRepository.findAllByUsers_UserId(Utils.toUUID(currentUser.getId())));
    }

    @Override
    public Optional<Accountancy> findByKeyAndUser(String accountancyKey,
                                                  User currentUser) {
        return AccountancyEntityMapper.toDomain(accountancyRepository.findByKeyAndUsers_UserId(accountancyKey, Utils.toUUID(currentUser.getId())));
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
