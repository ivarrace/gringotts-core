package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.application.ports.data.CategoryRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.CategoryEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.CategoryEntityMapper;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataCategoryRepository;
import com.ivarrace.gringotts.infrastructure.db.springdata.utils.ExampleGenerator;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

    private final SpringDataCategoryRepository categoryRepository;

    public CategoryRepositoryAdapter(SpringDataCategoryRepository categoryRepository) {
        this.categoryRepository=categoryRepository;
    }

    @Override
    public List<Category> findAll(User currentUser, String accountancyKey, GroupType groupType, String groupKey) {
        Example<CategoryEntity> example = ExampleGenerator.getCategoryExample(currentUser, Optional.of(accountancyKey), Optional.of(groupType), Optional.of(groupKey), Optional.empty());
        return CategoryEntityMapper.toDomainList(categoryRepository.findAll(example));
    }

    @Override
    public Optional<Category> findOne(User currentUser, String accountancyKey, GroupType groupType, String groupKey, String categoryKey) {
        Example<CategoryEntity> example = ExampleGenerator.getCategoryExample(currentUser, Optional.of(accountancyKey), Optional.of(groupType), Optional.of(groupKey), Optional.of(categoryKey));
        return CategoryEntityMapper.toDomain(categoryRepository.findOne(example));
    }

    @Override
    public Category save(Category category) {
        CategoryEntity newCategory = CategoryEntityMapper.toDbo(category);
        return CategoryEntityMapper.toDomain(categoryRepository.save(newCategory));
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(CategoryEntityMapper.toDbo(category));
    }

}
