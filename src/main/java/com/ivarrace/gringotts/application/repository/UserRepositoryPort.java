package com.ivarrace.gringotts.application.repository;

import com.ivarrace.gringotts.domain.user.User;

public interface UserRepositoryPort {

    User save(User user);

}
