package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.application.ports.data.CategoryRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.CategoryEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.CategoryEntityMapper;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataCategoryRepository;
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
    public List<Category> findAllByGroup(String groupKey) {
        return CategoryEntityMapper.toDomainList(categoryRepository.findAllByGroup_key(groupKey));
    }

    @Override
    public Optional<Category> findByKeyAndGroup(String categoryKey, String groupKey, GroupType groupType, String accountancyKey) {
        return CategoryEntityMapper.toDomain(
                categoryRepository.findByKeyAndGroup_keyAndGroup_typeAndGroup_Accountancy_key(categoryKey, groupKey, groupType.name(), accountancyKey));
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
