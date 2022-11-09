package com.ivarrace.gringotts.application.exception;

import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;

public class InsufficientPrivilegesException extends RuntimeException {

    public InsufficientPrivilegesException(AccountancyUserRoleType accountancyUserRoleType) {
        super("Insufficient privileges. Minimum required role: "+accountancyUserRoleType.name());
    }

}
