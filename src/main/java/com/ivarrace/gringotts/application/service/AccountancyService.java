package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.ports.data.AccountancyRepositoryPort;
import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRole;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;
import com.ivarrace.gringotts.domain.exception.ObjectAlreadyRegisteredException;
import com.ivarrace.gringotts.domain.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AccountancyService {

    private final AuthPort authPort;
    private final AccountancyRepositoryPort accountancyRepositoryPort;
    private final SummaryService summaryService;

    public AccountancyService(AuthPort authPort,
                              AccountancyRepositoryPort accountancyRepositoryPort,
                              SummaryService summaryService) {
        this.accountancyRepositoryPort = accountancyRepositoryPort;
        this.authPort = authPort;
        this.summaryService = summaryService;
    }

    public List<Accountancy> findAll() {
        return accountancyRepositoryPort.findAll(authPort.getCurrentUser());
    }

    public Accountancy findOne(String accountancyKey) {
        return accountancyRepositoryPort.findOne(authPort.getCurrentUser(), accountancyKey).orElseThrow(() -> new ObjectNotFoundException(accountancyKey));
    }

    public Accountancy findOneWithSummary(String accountancyKey, Optional<Year> year) {
        Accountancy accountancy = this.findOne(accountancyKey);
        return summaryService.generateAnnualSummaryForAccountancy(accountancy, year);
    }

    public Accountancy create(Accountancy accountancy) throws ObjectAlreadyRegisteredException {
        validateIfExists(accountancy);
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(authPort.getCurrentUser());
        userRole.setRole(AccountancyUserRoleType.OWNER);
        accountancy.setUsers(List.of(userRole));
        return accountancyRepositoryPort.save(accountancy);
    }

    public Accountancy modify(String accountancyKey, Accountancy accountancy) throws ObjectNotFoundException,
            ObjectAlreadyRegisteredException {
        validateIfExists(accountancy);
        Accountancy existing = this.findOne(accountancyKey);
        accountancy.setId(existing.getId());
        accountancy.setUsers(existing.getUsers());
        return accountancyRepositoryPort.save(accountancy);
    }

    public void delete(String accountancyKey) {
        Accountancy existing = this.findOne(accountancyKey);
        accountancyRepositoryPort.delete(existing);
    }

    private void validateIfExists(Accountancy accountancy) throws ObjectAlreadyRegisteredException {
        Optional<Accountancy> persistedAccountancy = accountancyRepositoryPort.findOne(authPort.getCurrentUser(), accountancy.getKey());
        if (persistedAccountancy.isPresent()) {
            throw new ObjectAlreadyRegisteredException(accountancy.getKey());
        }
    }
}
