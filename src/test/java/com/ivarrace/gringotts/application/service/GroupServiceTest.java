package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.TestUtils;
import com.ivarrace.gringotts.application.ports.data.GroupRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.exception.ObjectAlreadyRegisteredException;
import com.ivarrace.gringotts.domain.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@Tag("UnitTest")
class GroupServiceTest {

    private GroupRepositoryPort groupRepositoryMock;
    private AccountancyService accountancyServiceMock;

    private GroupService groupService;

    @BeforeEach
    public void init() {
        groupRepositoryMock = mock(GroupRepositoryPort.class);
        accountancyServiceMock = mock(AccountancyService.class);
        groupService = new GroupService(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void findAll() {
        Group group = TestUtils.fakerGroup();
        groupService.findByAccountancyKeyAndType(group.getAccountancy().getKey(), group.getType());
        verify(groupRepositoryMock, times(1)).findAllByTypeAndAccountancy(group.getType(),
                group.getAccountancy().getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void findByKey() {
        Group group = TestUtils.fakerGroup();
        when(groupRepositoryMock.findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                group.getAccountancy().getKey())).thenReturn(Optional.of(group));
        Group result = groupService.findByKey(group.getKey(), group.getAccountancy().getKey(), group.getType());
        assertNotNull(result);
        assertAll("Expected values", () -> assertEquals(group.getId(), result.getId()),
                () -> assertEquals(group.getKey(), result.getKey()), () -> assertEquals(group.getCreatedDate(),
                        result.getCreatedDate()), () -> assertEquals(group.getLastModified(),
                        result.getLastModified()), () -> assertEquals(group.getName(), result.getName()),
                () -> assertEquals(group.getCategories().size(), result.getCategories().size()),
                () -> assertEquals(group.getAccountancy().getKey(), result.getAccountancy().getKey()));
        verify(groupRepositoryMock, times(1)).findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                group.getAccountancy().getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void findByKey_notFound() {
        Group group = TestUtils.fakerGroup();
        when(groupRepositoryMock.findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                group.getAccountancy().getKey())).thenReturn(Optional.empty());

        String groupKey = group.getKey();
        String accountancyKey = group.getAccountancy().getKey();
        GroupType groupType = group.getType();
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            groupService.findByKey(groupKey, accountancyKey, groupType);
        });
        assertTrue(thrown.getMessage().contains(group.getKey()));
        verify(groupRepositoryMock, times(1)).findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                group.getAccountancy().getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void create() {
        Accountancy existingAccountancy = TestUtils.fakerAccountancy();
        Group group = TestUtils.fakerGroup();
        Accountancy groupAccountancyReq = new Accountancy();
        groupAccountancyReq.setKey(existingAccountancy.getKey());
        when(groupRepositoryMock.findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                existingAccountancy.getKey())).thenReturn(Optional.empty());
        when(accountancyServiceMock.findByKey(existingAccountancy.getKey())).thenReturn(existingAccountancy);
        when(groupRepositoryMock.save(group)).thenReturn(group);
        group.setAccountancy(existingAccountancy);
        Group result = groupService.create(group);
        assertNotNull(result);
        assertEquals(existingAccountancy, result.getAccountancy());
        verify(groupRepositoryMock, times(1)).findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                existingAccountancy.getKey());
        verify(groupRepositoryMock, times(1)).save(group);
        verify(accountancyServiceMock, times(1)).findByKey(existingAccountancy.getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void create_alreadyExists() {
        Accountancy existingAccountancy = TestUtils.fakerAccountancy();
        Group group = TestUtils.fakerGroup();
        group.setAccountancy(existingAccountancy);
        when(groupRepositoryMock.findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                existingAccountancy.getKey())).thenReturn(Optional.of(group));
        when(accountancyServiceMock.findByKey(existingAccountancy.getKey())).thenReturn(existingAccountancy);
        when(groupRepositoryMock.save(group)).thenReturn(group);
        ObjectAlreadyRegisteredException thrown = assertThrows(ObjectAlreadyRegisteredException.class, () -> {
            groupService.create(group);
        });
        assertTrue(thrown.getMessage().contains(group.getKey()));
        verify(groupRepositoryMock, times(1)).findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                existingAccountancy.getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void create_invalidAccountancy() {
        Group group = TestUtils.fakerGroup();
        when(groupRepositoryMock.findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                group.getAccountancy().getKey())).thenReturn(Optional.empty());
        when(accountancyServiceMock.findByKey(group.getAccountancy().getKey())).thenThrow(new ObjectNotFoundException(group.getAccountancy().getKey()));
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            groupService.create(group);
        });
        assertTrue(thrown.getMessage().contains(group.getAccountancy().getKey()));
        verify(groupRepositoryMock, times(1)).findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                group.getAccountancy().getKey());
        verify(accountancyServiceMock, times(1)).findByKey(group.getAccountancy().getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void modifyByKey() {
        Group existing = TestUtils.fakerGroup();
        Group modified = TestUtils.fakerGroup();
        modified.setId(existing.getId());
        modified.setAccountancy(existing.getAccountancy());
        when(groupRepositoryMock.findByKeyAndTypeAndAccountancy(existing.getKey(), modified.getType(),
                modified.getAccountancy().getKey())).thenReturn(Optional.of(existing));
        when(groupRepositoryMock.save(modified)).thenReturn(modified);
        Group result = groupService.modify(existing.getKey(), modified);
        assertNotNull(result);
        verify(groupRepositoryMock, times(1)).findByKeyAndTypeAndAccountancy(existing.getKey(), modified.getType(),
                modified.getAccountancy().getKey());
        verify(groupRepositoryMock, times(1)).save(modified);
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void modifyByKey_notFound() {
        Group existing = TestUtils.fakerGroup();
        Group modified = TestUtils.fakerGroup();
        modified.setId(existing.getId());
        modified.setAccountancy(existing.getAccountancy());
        when(groupRepositoryMock.findByKeyAndTypeAndAccountancy(existing.getKey(), modified.getType(),
                modified.getAccountancy().getKey())).thenReturn(Optional.empty());
        String existingGroupKey = existing.getKey();
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            groupService.modify(existingGroupKey, modified);
        });
        assertTrue(thrown.getMessage().contains(existing.getKey()));
        verify(groupRepositoryMock, times(1)).findByKeyAndTypeAndAccountancy(existing.getKey(), modified.getType(),
                modified.getAccountancy().getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void deleteByKey() {
        Group group = TestUtils.fakerGroup();
        when(groupRepositoryMock.findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                group.getAccountancy().getKey())).thenReturn(Optional.of(group));
        groupService.delete(group.getAccountancy().getKey(), group.getKey(), group.getType());
        verify(groupRepositoryMock, times(1)).findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                group.getAccountancy().getKey());
        verify(groupRepositoryMock, times(1)).delete(group);
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void deleteByKey_notFound() {
        Group group = TestUtils.fakerGroup();
        when(groupRepositoryMock.findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                group.getAccountancy().getKey())).thenReturn(Optional.empty());
        String groupKey = group.getKey();
        String accountancyKey = group.getAccountancy().getKey();
        GroupType groupType = group.getType();
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            groupService.delete(accountancyKey, groupKey, groupType);
        });
        assertTrue(thrown.getMessage().contains(group.getKey()));
        verify(groupRepositoryMock, times(1)).findByKeyAndTypeAndAccountancy(group.getKey(), group.getType(),
                group.getAccountancy().getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

}