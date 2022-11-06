package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.exception.ObjectAlreadyRegisteredException;
import com.ivarrace.gringotts.application.exception.ObjectNotFoundException;
import com.ivarrace.gringotts.application.ports.AuthPort;
import com.ivarrace.gringotts.application.repository.AccountancyRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRole;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class AccountancyService {

    private final AccountancyRepositoryPort accountancyRepositoryPort;
    private final AuthPort authPort;

    public AccountancyService(AccountancyRepositoryPort accountancyRepositoryPort,
                              AuthPort authPort) {
        this.accountancyRepositoryPort = accountancyRepositoryPort;
        this.authPort = authPort;
    }

    public List<Accountancy> findAll() {
        return accountancyRepositoryPort.findAllByUser(authPort.getCurrentUser());
    }

    public Accountancy findByKey(String accountancyKey) {
        return accountancyRepositoryPort.findByKeyAndUser(accountancyKey, authPort.getCurrentUser()).orElseThrow(() -> new ObjectNotFoundException(accountancyKey));
    }

    public Accountancy create(Accountancy accountancy) {
        Optional<Accountancy> existing = accountancyRepositoryPort.findByKeyAndUser(accountancy.getKey(), authPort.getCurrentUser());
        if(existing.isPresent()){
            throw new ObjectAlreadyRegisteredException(accountancy.getKey());
        }
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(authPort.getCurrentUser());
        userRole.setRole(AccountancyUserRoleType.OWNER);
        accountancy.setUsers(List.of(userRole));
        return accountancyRepositoryPort.save(accountancy);
    }

    public Accountancy modifyByKey(String accountancyKey, Accountancy accountancy) throws ObjectNotFoundException {
        Accountancy existing = this.findByKey(accountancyKey);
        accountancy.setId(existing.getId());
        accountancy.setUsers(existing.getUsers());
        return accountancyRepositoryPort.save(accountancy);
    }

    public void deleteByKey(String accountancyKey) {
        Accountancy existing = this.findByKey(accountancyKey);
        accountancyRepositoryPort.delete(existing);
    }
}
