package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.ports.data.CategoryRepositoryPort;
import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.exception.ObjectAlreadyRegisteredException;
import com.ivarrace.gringotts.domain.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CategoryService {

    private final AuthPort authPort;
    private final GroupService groupService;
    private final CategoryRepositoryPort categoryRepositoryPort;

    public CategoryService(AuthPort authPort,
                           GroupService groupService,
                           CategoryRepositoryPort categoryRepositoryPort) {
        this.authPort = authPort;
        this.categoryRepositoryPort = categoryRepositoryPort;
        this.groupService = groupService;
    }

    public List<Category> findAll(String accountancyKey, GroupType groupType, String groupKey) {
        return categoryRepositoryPort.findAll(authPort.getCurrentUser(), accountancyKey, groupType, groupKey);
    }

    public Category findOne(String accountancyKey, GroupType groupType, String groupKey, String categoryKey) throws ObjectNotFoundException {
        return categoryRepositoryPort.findOne(
                authPort.getCurrentUser(), accountancyKey, groupType,
                groupKey, categoryKey).orElseThrow(() -> new ObjectNotFoundException(categoryKey));
    }

    public Category create(Category category) throws ObjectAlreadyRegisteredException {
        validateIfExists(category);
        Group group = groupService.findOne(category.getGroup().getKey(),
                category.getGroup().getAccountancy().getKey(), category.getGroup().getType());
        category.setGroup(group);
        return categoryRepositoryPort.save(category);
    }

    public Category modify(String categoryKey, Category category) throws ObjectNotFoundException,
            ObjectAlreadyRegisteredException {
        validateIfExists(category);
        Category existing = this.findOne(
                category.getGroup().getAccountancy().getKey(),
                category.getGroup().getType(),
                category.getGroup().getKey(),
                categoryKey);
        category.setId(existing.getId());
        category.setGroup(existing.getGroup());
        return categoryRepositoryPort.save(category);
    }

    public void delete(String categoryKey, String groupKey, GroupType groupType, String accountancyKey) throws ObjectNotFoundException {
        Category existing = this.findOne(accountancyKey, groupType, groupKey, categoryKey);
        categoryRepositoryPort.delete(existing);
    }

    private void validateIfExists(Category category) throws ObjectAlreadyRegisteredException {
        Optional<Category> persistedCategory = categoryRepositoryPort.findOne(
                authPort.getCurrentUser(),
                category.getGroup().getAccountancy().getKey(),
                category.getGroup().getType(),
                category.getGroup().getKey(),
                category.getKey());
        if (persistedCategory.isPresent()) {
            throw new ObjectAlreadyRegisteredException(persistedCategory.get().getKey());
        }
    }
}
