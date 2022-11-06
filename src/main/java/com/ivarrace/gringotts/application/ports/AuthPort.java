package com.ivarrace.gringotts.application.ports;

import com.ivarrace.gringotts.domain.user.User;

public interface AuthPort {

    User getCurrentUser();

}
