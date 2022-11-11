package com.ivarrace.gringotts.domain.exception;

import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;

public class InsufficientPrivilegesException extends RuntimeException {

    public InsufficientPrivilegesException(AccountancyUserRoleType requiredRole, AccountancyUserRoleType actualRole) {
        super(String.format("Insufficient privileges. Minimum required role: [%s]. Actual role: [%s]",
                requiredRole.name(), actualRole.name()));
    }

}
