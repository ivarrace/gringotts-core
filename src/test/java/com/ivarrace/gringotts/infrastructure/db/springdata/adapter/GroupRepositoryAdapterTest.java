package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.TestUtils;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.GroupEntity;
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
class GroupRepositoryAdapterTest {

    private SpringDataGroupRepository springDataRepositoryMock;

    private GroupRepositoryAdapter repositoryAdapter;

    @BeforeEach
    public void init() {
        springDataRepositoryMock = mock(SpringDataGroupRepository.class);
        repositoryAdapter = new GroupRepositoryAdapter(springDataRepositoryMock);
    }

    @Test
    void findAllByTypeInAccountancy() {
        GroupEntity entityExample = TestUtils.fakerGroupEntity();
        when(springDataRepositoryMock.findAllByTypeAndAccountancy_key(entityExample.getType(),
                entityExample.getAccountancy().getKey())).thenReturn(Collections.singletonList(entityExample));
        List<Group> result = repositoryAdapter.findAllByTypeAndAccountancy(GroupType.valueOf(entityExample.getType())
                , entityExample.getAccountancy().getKey());
        assertEquals(1, result.size());
        verify(springDataRepositoryMock, times(1)).findAllByTypeAndAccountancy_key(entityExample.getType(),
                entityExample.getAccountancy().getKey());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void findByKeyAndTypeInAccountancy_empty() {
        GroupEntity entityExample = TestUtils.fakerGroupEntity();
        when(springDataRepositoryMock.findByKeyAndTypeAndAccountancy_key(entityExample.getKey(),
                entityExample.getType(), entityExample.getAccountancy().getKey())).thenReturn(Optional.empty());
        Optional<Group> result = repositoryAdapter.findByKeyAndTypeAndAccountancy(entityExample.getKey(),
                GroupType.valueOf(entityExample.getType()), entityExample.getAccountancy().getKey());
        assertTrue(result.isEmpty());
        verify(springDataRepositoryMock, times(1)).findByKeyAndTypeAndAccountancy_key(entityExample.getKey(),
                entityExample.getType(), entityExample.getAccountancy().getKey());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void findByKeyAndTypeInAccountancy() {
        GroupEntity entityExample = TestUtils.fakerGroupEntity();
        when(springDataRepositoryMock.findByKeyAndTypeAndAccountancy_key(entityExample.getKey(),
                entityExample.getType(), entityExample.getAccountancy().getKey())).thenReturn(Optional.of(entityExample));
        Optional<Group> result = repositoryAdapter.findByKeyAndTypeAndAccountancy(entityExample.getKey(),
                GroupType.valueOf(entityExample.getType()), entityExample.getAccountancy().getKey());
        assertTrue(result.isPresent());
        verify(springDataRepositoryMock, times(1)).findByKeyAndTypeAndAccountancy_key(entityExample.getKey(),
                entityExample.getType(), entityExample.getAccountancy().getKey());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void save() {
        GroupEntity entityExample = TestUtils.fakerGroupEntity();
        when(springDataRepositoryMock.save(any())).thenReturn(entityExample);
        Group result = repositoryAdapter.save(any());
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