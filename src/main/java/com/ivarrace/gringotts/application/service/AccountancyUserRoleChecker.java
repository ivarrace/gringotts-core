package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRole;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;
import com.ivarrace.gringotts.domain.exception.InsufficientPrivilegesException;
import com.ivarrace.gringotts.domain.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountancyUserRoleChecker {

    private final AuthPort authPort;
    private final AccountancyService accountancyService;

    public AccountancyUserRoleChecker(AuthPort authPort, AccountancyService accountancyService) {
        this.authPort = authPort;
        this.accountancyService = accountancyService;
    }

    public boolean hasPermission(String accountancyKey, AccountancyUserRoleType accountancyUserRoleType) {
        AccountancyUserRole accountancyUserRole =
                accountancyService.findOne(accountancyKey).getUsers().stream()
                        .filter(userRoles -> authPort.getCurrentUser().getId().equals(userRoles.getUser().getId()))
                        .findFirst()
                        .orElseThrow(() -> new ObjectNotFoundException(accountancyKey));
        switch (accountancyUserRoleType) {
            case OWNER:
                if (AccountancyUserRoleType.OWNER.equals(accountancyUserRole.getRole())) {
                    return true;
                } else {
                    throw new InsufficientPrivilegesException(accountancyUserRoleType, accountancyUserRole.getRole());
                }
            case EDITOR:
                if (AccountancyUserRoleType.EDITOR.equals(accountancyUserRole.getRole()) || AccountancyUserRoleType.OWNER.equals(accountancyUserRole.getRole())) {
                    return true;
                } else {
                    throw new InsufficientPrivilegesException(accountancyUserRoleType, accountancyUserRole.getRole());
                }
            case VIEWER:
                if (AccountancyUserRoleType.VIEWER.equals(accountancyUserRole.getRole()) || AccountancyUserRoleType.EDITOR.equals(accountancyUserRole.getRole()) || AccountancyUserRoleType.OWNER.equals(accountancyUserRole.getRole())) {
                    return true;
                } else {
                    throw new InsufficientPrivilegesException(accountancyUserRoleType, accountancyUserRole.getRole());
                }
            default:
                return false;
        }
    }

    public void validatePermission(String accountancyKey, AccountancyUserRoleType accountancyUserRoleType) {
        hasPermission(accountancyKey, accountancyUserRoleType);
    }

}
