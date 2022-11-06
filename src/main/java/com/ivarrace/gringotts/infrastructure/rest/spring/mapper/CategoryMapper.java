package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.Utils;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.CategoryResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.NewCategoryCommand;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    private CategoryMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setCreatedDate(category.getCreatedDate());
        response.setLastModified(category.getLastModified());
        response.setKey(category.getKey());
        return response;
    }

    public static List<CategoryResponse> toResponse(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return Collections.emptyList();
        }
        return categories.stream().map(CategoryMapper::toResponse).collect(Collectors.toList());
    }

    public static Category toDomain(NewCategoryCommand command,
                                    GroupType groupType,
                                    String accountancyKey,
                                    String groupKey) {
        if (command == null) {
            return null;
        }
        Category category = new Category();
        category.setName(command.getName());
        category.setKey(Utils.nameToKey(command.getName()));
        Accountancy accountancy = new Accountancy();
        accountancy.setKey(accountancyKey);
        Group group = new Group();
        group.setKey(groupKey);
        group.setType(groupType);
        group.setAccountancy(accountancy);
        category.setGroup(group);
        return category;
    }

}