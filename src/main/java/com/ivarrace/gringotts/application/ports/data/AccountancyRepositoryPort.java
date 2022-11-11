package com.ivarrace.gringotts.application.ports.data;

import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface AccountancyRepositoryPort {

    List<Accountancy> findAllByUser(User currentUser);

    Optional<Accountancy> findByKeyAndUser(String accountancyKey, User currentUser);

    Accountancy save(Accountancy accountancy);

    void delete(Accountancy accountancy);
}
