package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.TestUtils;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.MovementEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataMovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@Tag("UnitTest")
class MovementRepositoryAdapterTest {

    private final UUID CURRENT_USER_UUID = UUID.randomUUID();
    private SpringDataMovementRepository springDataRepositoryMock;
    private MovementRepositoryAdapter repositoryAdapter;
    private User CURRENT_USER;

    @BeforeEach
    public void init() {
        springDataRepositoryMock = mock(SpringDataMovementRepository.class);
        repositoryAdapter = new MovementRepositoryAdapter(springDataRepositoryMock);
        CURRENT_USER = new User();
        CURRENT_USER.setId(CURRENT_USER_UUID.toString());
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