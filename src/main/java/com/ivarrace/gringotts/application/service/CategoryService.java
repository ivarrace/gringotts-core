package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.exception.ObjectAlreadyRegisteredException;
import com.ivarrace.gringotts.application.exception.ObjectNotFoundException;
import com.ivarrace.gringotts.application.repository.CategoryRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CategoryService {

    private final CategoryRepositoryPort categoryRepositoryPort;
    private final GroupService groupService;

    public CategoryService(CategoryRepositoryPort categoryRepositoryPort,
                           GroupService groupService) {
        this.categoryRepositoryPort = categoryRepositoryPort;
        this.groupService=groupService;
    }

    public List<Category> findAllInGroup(String groupKey) {
        return categoryRepositoryPort.findAllInGroup(groupKey);
    }

    public Category findByKeyInGroup(String categoryKey, String groupKey, GroupType groupType, String accountancyKey) throws ObjectNotFoundException {
        return categoryRepositoryPort.findByKeyInGroup(categoryKey, groupKey, groupType, accountancyKey)
                .orElseThrow(() -> new ObjectNotFoundException(categoryKey));
    }

    public Category create(Category category) {
        Optional<Category> existing =
                categoryRepositoryPort.findByKeyInGroup(category.getKey(), category.getGroup().getKey(), category.getGroup().getType(), category.getGroup().getAccountancy().getKey());
        if(existing.isPresent()){
            throw new ObjectAlreadyRegisteredException(category.getKey());
        }
        Group group = groupService.findByKey(category.getGroup().getKey(), category.getGroup().getAccountancy().getKey(), category.getGroup().getType());
        category.setGroup(group);
        return categoryRepositoryPort.save(category);
    }

    public Category modify(String categoryKey, Category category) throws ObjectNotFoundException {
        Category existing = this.findByKeyInGroup(categoryKey, category.getGroup().getKey(), category.getGroup().getType(), category.getGroup().getAccountancy().getKey());
        category.setId(existing.getId());
        category.setGroup(existing.getGroup());
        return categoryRepositoryPort.save(category);
    }

    public void delete(String categoryKey, String groupKey, GroupType groupType, String accountancyKey) throws ObjectNotFoundException {
        Category existing = this.findByKeyInGroup(categoryKey, groupKey, groupType, accountancyKey);
        categoryRepositoryPort.delete(existing);
    }
}
