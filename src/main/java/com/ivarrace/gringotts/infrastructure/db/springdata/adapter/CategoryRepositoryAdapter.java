package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.application.repository.CategoryRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.Category;
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
    public List<Category> findAllInGroup(String groupKey) {
        return CategoryEntityMapper.toDomainList(categoryRepository.findAllByGroup_key(groupKey));
    }

    @Override
    public Optional<Category> findByKeyInGroup(String categoryKey, String groupKey) {
        return CategoryEntityMapper.toDomain(
                categoryRepository.findByKeyAndGroup_key(categoryKey, groupKey));
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
