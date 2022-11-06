package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.exception.ObjectNotFoundException;
import com.ivarrace.gringotts.application.ports.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRole;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountancyUserRoleChecker {

    private final AccountancyService accountancyService;
    private final AuthPort authPort;

    public AccountancyUserRoleChecker(AccountancyService accountancyService,
                                      AuthPort authPort) {
        this.accountancyService = accountancyService;
        this.authPort = authPort;
    }

    public boolean hasPermission(String accountancyKey,
                                 AccountancyUserRoleType accountancyUserRoleType) {
        AccountancyUserRole accountancyUserRole =
                accountancyService.findByKey(accountancyKey).getUsers().stream()
                        .filter(userRoles -> authPort.getCurrentUser().getId().equals(userRoles.getUser().getId()))
                        .findFirst()
                        .orElseThrow(() -> new ObjectNotFoundException(accountancyKey));
        switch (accountancyUserRoleType) {
            case OWNER:
                return AccountancyUserRoleType.OWNER.equals(accountancyUserRole.getRole());
            case EDITOR:
                return AccountancyUserRoleType.EDITOR.equals(accountancyUserRole.getRole()) ||
                        AccountancyUserRoleType.OWNER.equals(accountancyUserRole.getRole());
            case VIEWER:
                return AccountancyUserRoleType.VIEWER.equals(accountancyUserRole.getRole()) ||
                        AccountancyUserRoleType.EDITOR.equals(accountancyUserRole.getRole()) ||
                        AccountancyUserRoleType.OWNER.equals(accountancyUserRole.getRole());
            default:
                return false;
        }
    }

}
