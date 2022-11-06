package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.TestUtils;
import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.CategoryEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.MovementEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataCategoryRepository;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataMovementRepository;
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
class MovementRepositoryAdapterTest {

    private SpringDataMovementRepository springDataRepositoryMock;

    private MovementRepositoryAdapter repositoryAdapter;

    @BeforeEach
    public void init() {
        springDataRepositoryMock = mock(SpringDataMovementRepository.class);
        repositoryAdapter = new MovementRepositoryAdapter(springDataRepositoryMock);
    }

    @Test
    void findAllByCategory() {
        MovementEntity entityExample = TestUtils.fakerMovementEntity();
        when(springDataRepositoryMock.findAllByCategory_key(entityExample.getCategory().getKey()))
                .thenReturn(Collections.singletonList(entityExample));
        List<Movement> result = repositoryAdapter.findAllByCategory(entityExample.getCategory().getKey());
        assertEquals(1, result.size());
        verify(springDataRepositoryMock, times(1)).findAllByCategory_key(entityExample.getCategory().getKey());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void findById_empty() {
        MovementEntity entityExample = TestUtils.fakerMovementEntity();
        when(springDataRepositoryMock.findById(entityExample.getId()))
                .thenReturn(Optional.empty());
        Optional<Movement> result =
                repositoryAdapter.findById(entityExample.getId().toString());
        assertTrue(result.isEmpty());
        verify(springDataRepositoryMock, times(1)).findById(entityExample.getId());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void findById() {
        MovementEntity entityExample = TestUtils.fakerMovementEntity();
        when(springDataRepositoryMock.findById(entityExample.getId()))
                .thenReturn(Optional.of(entityExample));
        Optional<Movement> result =
                repositoryAdapter.findById(entityExample.getId().toString());
        assertTrue(result.isPresent());
        verify(springDataRepositoryMock, times(1)).findById(entityExample.getId());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void save() {
        MovementEntity entityExample = TestUtils.fakerMovementEntity();
        when(springDataRepositoryMock.save(any())).thenReturn(entityExample);
        Movement result = repositoryAdapter.save(any());
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