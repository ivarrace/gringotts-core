package com.ivarrace.gringotts.application.ports.data;

import com.ivarrace.gringotts.domain.user.User;

public interface UserRepositoryPort {

    User save(User user);

}
