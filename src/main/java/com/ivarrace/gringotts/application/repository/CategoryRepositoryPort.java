package com.ivarrace.gringotts.application.repository;

import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.GroupType;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {

    List<Category> findAllInGroup(String groupKey);

    Optional<Category> findByKeyInGroup(String categoryKey, String groupKey, GroupType groupType, String accountancyKey);

    Category save(Category category);

    void delete(Category category);
}
