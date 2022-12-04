package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.FakerGenerator;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.GroupEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataGroupRepository;
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
class GroupRepositoryAdapterTest {

    private final UUID CURRENT_USER_UUID = UUID.randomUUID();
    private User CURRENT_USER;
    private SpringDataGroupRepository springDataRepositoryMock;

    private GroupRepositoryAdapter repositoryAdapter;

    @BeforeEach
    public void init() {
        springDataRepositoryMock = mock(SpringDataGroupRepository.class);
        repositoryAdapter = new GroupRepositoryAdapter(springDataRepositoryMock);
        CURRENT_USER = new User();
        CURRENT_USER.setId(CURRENT_USER_UUID.toString());
    }

    @Test
    void findAllByTypeInAccountancy() {
        GroupEntity entityExample = FakerGenerator.fakerGroupEntity();
        when(springDataRepositoryMock.findAll(any(Example.class))).thenReturn(Collections.singletonList(entityExample));
        List<Group> result = repositoryAdapter.findAll(CURRENT_USER,
                entityExample.getAccountancy().getKey(), GroupType.valueOf(entityExample.getType())
        );
        assertEquals(1, result.size());
        verify(springDataRepositoryMock, times(1)).findAll(any(Example.class));
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void findByKeyAndTypeInAccountancy_empty() {
        GroupEntity entityExample = FakerGenerator.fakerGroupEntity();
        when(springDataRepositoryMock.findOne(any(Example.class))).thenReturn(Optional.empty());
        Optional<Group> result = repositoryAdapter.findOne(CURRENT_USER,
                entityExample.getAccountancy().getKey(),
                GroupType.valueOf(entityExample.getType()), entityExample.getKey());
        assertTrue(result.isEmpty());
        verify(springDataRepositoryMock, times(1)).findOne(any(Example.class));
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void findByKeyAndTypeInAccountancy() {
        GroupEntity entityExample = FakerGenerator.fakerGroupEntity();
        when(springDataRepositoryMock.findOne(any(Example.class))).thenReturn(Optional.of(entityExample));
        Optional<Group> result = repositoryAdapter.findOne(CURRENT_USER,
                entityExample.getAccountancy().getKey(),
                GroupType.valueOf(entityExample.getType()), entityExample.getKey());
        assertTrue(result.isPresent());
        verify(springDataRepositoryMock, times(1)).findOne(any(Example.class));
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void save() {
        GroupEntity entityExample = FakerGenerator.fakerGroupEntity();
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