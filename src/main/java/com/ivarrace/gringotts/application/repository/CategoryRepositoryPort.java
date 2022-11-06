package com.ivarrace.gringotts.application.repository;

import com.ivarrace.gringotts.domain.accountancy.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {

    List<Category> findAllInGroup(String groupKey);

    Optional<Category> findByKeyInGroup(String categoryKey, String groupKey);

    Category save(Category category);

    void delete(Category category);
}
