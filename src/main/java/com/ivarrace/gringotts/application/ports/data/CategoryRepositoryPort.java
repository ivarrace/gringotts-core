package com.ivarrace.gringotts.application.ports.data;

import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.GroupType;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {

    List<Category> findAllByGroup(String groupKey);

    Optional<Category> findByKeyAndGroup(String categoryKey, String groupKey, GroupType groupType,
                                         String accountancyKey);

    Category save(Category category);

    void delete(Category category);
}
