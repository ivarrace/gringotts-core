package com.ivarrace.gringotts.application.ports.security;

import com.ivarrace.gringotts.domain.user.User;

public interface AuthPort {

    User getCurrentUser();

}
