package com.ivarrace.gringotts.domain.accountancy;

import com.ivarrace.gringotts.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountancyUserRole {

    private String id;
    private User user;
    private AccountancyUserRoleType role;
}
