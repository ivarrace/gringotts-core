package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.FakerGenerator;
import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.CategoryEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@Tag("UnitTest")
class CategoryRepositoryAdapterTest {

    private final UUID CURRENT_USER_UUID = UUID.randomUUID();
    private User CURRENT_USER;
    private SpringDataCategoryRepository springDataRepositoryMock;

    private CategoryRepositoryAdapter repositoryAdapter;

    @BeforeEach
    public void init() {
        springDataRepositoryMock = mock(SpringDataCategoryRepository.class);
        repositoryAdapter = new CategoryRepositoryAdapter(springDataRepositoryMock);
        CURRENT_USER = new User();
        CURRENT_USER.setId(CURRENT_USER_UUID.toString());
    }

    @Test
    void findAllInGroup() {
        CategoryEntity entityExample = FakerGenerator.fakerCategoryEntity();
        when(springDataRepositoryMock.findAll(any(Example.class))).thenReturn(Collections.singletonList(entityExample));
        List<Category> result = repositoryAdapter.findAll(
                CURRENT_USER,
                entityExample.getGroup().getAccountancy().getKey(),
                GroupType.valueOf(entityExample.getGroup().getType()),
                entityExample.getGroup().getKey());
        assertEquals(1, result.size());
        verify(springDataRepositoryMock, times(1)).findAll(any(Example.class));
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void findByKeyInGroup_empty() {
        CategoryEntity entityExample = FakerGenerator.fakerCategoryEntity();
        when(springDataRepositoryMock.findOne(any(Example.class))).thenReturn(Optional.empty());
        Optional<Category> result = repositoryAdapter.findOne(
                CURRENT_USER,
                entityExample.getGroup().getAccountancy().getKey(),
                GroupType.valueOf(entityExample.getGroup().getType()),
                entityExample.getGroup().getKey(),
                entityExample.getKey());
        assertTrue(result.isEmpty());
        verify(springDataRepositoryMock, times(1)).findOne(any(Example.class));
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void findByKeyInGroup() {
        CategoryEntity entityExample = FakerGenerator.fakerCategoryEntity();
        when(springDataRepositoryMock.findOne(any(Example.class))).thenReturn(Optional.of(entityExample));
        Optional<Category> result = repositoryAdapter.findOne(
                CURRENT_USER,
                entityExample.getGroup().getAccountancy().getKey(),
                GroupType.valueOf(entityExample.getGroup().getType()),
                entityExample.getGroup().getKey(),
                entityExample.getKey());
        assertTrue(result.isPresent());
        verify(springDataRepositoryMock, times(1)).findOne(any(Example.class));
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void save() {
        CategoryEntity entityExample = FakerGenerator.fakerCategoryEntity();
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