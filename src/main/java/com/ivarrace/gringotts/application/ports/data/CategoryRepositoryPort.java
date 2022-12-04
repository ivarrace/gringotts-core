package com.ivarrace.gringotts.application.ports.data;

import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {

    List<Category> findAll(User currentUser, String accountancyKey, GroupType groupType, String groupKey);

    Optional<Category> findOne(User currentUser, String accountancyKey, GroupType groupType, String groupKey, String categoryKey);

    Category save(Category category);

    void delete(Category category);
}
