package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.TestUtils;
import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.CategoryEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.GroupEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataAccountancyRepository;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataCategoryRepository;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@Tag("UnitTest")
class CategoryRepositoryAdapterTest {

    private SpringDataCategoryRepository springDataRepositoryMock;

    private CategoryRepositoryAdapter repositoryAdapter;

    @BeforeEach
    public void init() {
        springDataRepositoryMock = mock(SpringDataCategoryRepository.class);
        repositoryAdapter = new CategoryRepositoryAdapter(springDataRepositoryMock);
    }

    @Test
    void findAllInGroup() {
        CategoryEntity entityExample = TestUtils.fakerCategoryEntity();
        when(springDataRepositoryMock.findAllByGroup_key(entityExample.getGroup().getKey()))
                .thenReturn(Collections.singletonList(entityExample));
        List<Category> result = repositoryAdapter.findAllInGroup(entityExample.getGroup().getKey());
        assertEquals(1, result.size());
        verify(springDataRepositoryMock, times(1)).findAllByGroup_key(entityExample.getGroup().getKey());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void findByKeyInGroup_empty() {
        CategoryEntity entityExample = TestUtils.fakerCategoryEntity();
        when(springDataRepositoryMock.findByKeyAndGroup_keyAndGroup_typeAndGroup_Accountancy_key(entityExample.getKey(), entityExample.getGroup().getKey(), entityExample.getGroup().getType(), entityExample.getGroup().getAccountancy().getKey()))
                .thenReturn(Optional.empty());
        Optional<Category> result =
                repositoryAdapter.findByKeyInGroup(entityExample.getKey(), entityExample.getGroup().getKey(), GroupType.valueOf(entityExample.getGroup().getType()), entityExample.getGroup().getAccountancy().getKey());
        assertTrue(result.isEmpty());
        verify(springDataRepositoryMock, times(1)).findByKeyAndGroup_keyAndGroup_typeAndGroup_Accountancy_key(entityExample.getKey(), entityExample.getGroup().getKey(), entityExample.getGroup().getType(), entityExample.getGroup().getAccountancy().getKey());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void findByKeyInGroup() {
        CategoryEntity entityExample = TestUtils.fakerCategoryEntity();
        when(springDataRepositoryMock.findByKeyAndGroup_keyAndGroup_typeAndGroup_Accountancy_key(entityExample.getKey(), entityExample.getGroup().getKey(), entityExample.getGroup().getType(), entityExample.getGroup().getAccountancy().getKey()))
                .thenReturn(Optional.of(entityExample));
        Optional<Category> result =
                repositoryAdapter.findByKeyInGroup(entityExample.getKey(), entityExample.getGroup().getKey(), GroupType.valueOf(entityExample.getGroup().getType()), entityExample.getGroup().getAccountancy().getKey());
        assertTrue(result.isPresent());
        verify(springDataRepositoryMock, times(1)).findByKeyAndGroup_keyAndGroup_typeAndGroup_Accountancy_key(entityExample.getKey(), entityExample.getGroup().getKey(), entityExample.getGroup().getType(), entityExample.getGroup().getAccountancy().getKey());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void save() {
        CategoryEntity entityExample = TestUtils.fakerCategoryEntity();
        when(springDataRepositoryMock.save(any())).thenReturn(entityExample);
        Category result = repositoryAdapter.save(any());
        assertNotNull(result);
        verify(springDataRepositoryMock, times(1)).save(any());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void delete() {
        repositoryAdapter.delete(any());
        verify(springDataRepositoryMock, times(1)).delete(any());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }
}