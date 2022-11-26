package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.ports.data.AccountancyRepositoryPort;
import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRole;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;
import com.ivarrace.gringotts.domain.exception.ObjectAlreadyRegisteredException;
import com.ivarrace.gringotts.domain.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class AccountancyService {

    private final AccountancyRepositoryPort accountancyRepositoryPort;
    private final AuthPort authPort;

    public AccountancyService(AccountancyRepositoryPort accountancyRepositoryPort, AuthPort authPort) {
        this.accountancyRepositoryPort = accountancyRepositoryPort;
        this.authPort = authPort;
    }

    public List<Accountancy> findAll() {
        return accountancyRepositoryPort.findAllByUser(authPort.getCurrentUser());
    }

    public Accountancy findByKey(String accountancyKey) {
        return accountancyRepositoryPort.findByKeyAndUser(accountancyKey, authPort.getCurrentUser()).orElseThrow(() -> new ObjectNotFoundException(accountancyKey));
    }

    public Accountancy create(Accountancy accountancy) throws ObjectAlreadyRegisteredException{
        validateIfExists(accountancy);
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(authPort.getCurrentUser());
        userRole.setRole(AccountancyUserRoleType.OWNER);
        accountancy.setUsers(List.of(userRole));
        return accountancyRepositoryPort.save(accountancy);
    }

    public Accountancy modifyByKey(String accountancyKey, Accountancy accountancy) throws ObjectNotFoundException, ObjectAlreadyRegisteredException {
        validateIfExists(accountancy);
        Accountancy existing = this.findByKey(accountancyKey);
        accountancy.setId(existing.getId());
        accountancy.setUsers(existing.getUsers());
        return accountancyRepositoryPort.save(accountancy);
    }

    public void deleteByKey(String accountancyKey) {
        Accountancy existing = this.findByKey(accountancyKey);
        accountancyRepositoryPort.delete(existing);
    }

    private void validateIfExists(Accountancy accountancy) throws ObjectAlreadyRegisteredException {
        Optional<Accountancy> persistedAccountancy = accountancyRepositoryPort.findByKeyAndUser(accountancy.getKey(),
                authPort.getCurrentUser());
        if (persistedAccountancy.isPresent()) {
            throw new ObjectAlreadyRegisteredException(accountancy.getKey());
        }
    }
}
