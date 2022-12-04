package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.FakerGenerator;
import com.ivarrace.gringotts.application.ports.data.GroupRepositoryPort;
import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.Group;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.exception.ObjectAlreadyRegisteredException;
import com.ivarrace.gringotts.domain.exception.ObjectNotFoundException;
import com.ivarrace.gringotts.domain.user.User;
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

    private static final User MOCK_USER = new User();
    private AuthPort authPortMock;
    private GroupRepositoryPort groupRepositoryMock;
    private AccountancyService accountancyServiceMock;

    private GroupService groupService;

    @BeforeEach
    public void init() {
        authPortMock = mock(AuthPort.class);
        groupRepositoryMock = mock(GroupRepositoryPort.class);
        accountancyServiceMock = mock(AccountancyService.class);
        groupService = new GroupService(authPortMock, accountancyServiceMock, groupRepositoryMock);
        when(authPortMock.getCurrentUser()).thenReturn(MOCK_USER);
    }

    @Test
    void findAll() {
        Group group = FakerGenerator.fakerGroup();
        groupService.findAll(group.getAccountancy().getKey(), group.getType());
        verify(groupRepositoryMock, times(1)).findAll(MOCK_USER, group.getAccountancy().getKey(),
                group.getType());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void findByKey() {
        Group group = FakerGenerator.fakerGroup();
        when(groupRepositoryMock.findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey())).thenReturn(Optional.of(group));
        Group result = groupService.findOne(group.getKey(), group.getAccountancy().getKey(), group.getType());
        assertNotNull(result);
        assertAll("Expected values", () -> assertEquals(group.getId(), result.getId()),
                () -> assertEquals(group.getKey(), result.getKey()), () -> assertEquals(group.getCreatedDate(),
                        result.getCreatedDate()), () -> assertEquals(group.getLastModified(),
                        result.getLastModified()), () -> assertEquals(group.getName(), result.getName()),
                () -> assertEquals(group.getCategories().size(), result.getCategories().size()),
                () -> assertEquals(group.getAccountancy().getKey(), result.getAccountancy().getKey()));
        verify(groupRepositoryMock, times(1)).findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void findByKey_notFound() {
        Group group = FakerGenerator.fakerGroup();
        when(groupRepositoryMock.findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey())).thenReturn(Optional.empty());

        String groupKey = group.getKey();
        String accountancyKey = group.getAccountancy().getKey();
        GroupType groupType = group.getType();
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            groupService.findOne(groupKey, accountancyKey, groupType);
        });
        assertTrue(thrown.getMessage().contains(group.getKey()));
        verify(groupRepositoryMock, times(1)).findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void create() {
        Accountancy existingAccountancy = FakerGenerator.fakerAccountancy();
        Group group = FakerGenerator.fakerGroup();
        Accountancy groupAccountancyReq = new Accountancy();
        groupAccountancyReq.setKey(existingAccountancy.getKey());
        when(groupRepositoryMock.findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey())).thenReturn(Optional.empty());
        when(accountancyServiceMock.findOne(existingAccountancy.getKey())).thenReturn(existingAccountancy);
        when(groupRepositoryMock.save(group)).thenReturn(group);
        group.setAccountancy(existingAccountancy);
        Group result = groupService.create(group);
        assertNotNull(result);
        assertEquals(existingAccountancy, result.getAccountancy());
        verify(groupRepositoryMock, times(1)).findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey());
        verify(groupRepositoryMock, times(1)).save(group);
        verify(accountancyServiceMock, times(1)).findOne(existingAccountancy.getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void create_alreadyExists() {
        Accountancy existingAccountancy = FakerGenerator.fakerAccountancy();
        Group group = FakerGenerator.fakerGroup();
        group.setAccountancy(existingAccountancy);
        when(groupRepositoryMock.findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey())).thenReturn(Optional.of(group));
        when(accountancyServiceMock.findOne(existingAccountancy.getKey())).thenReturn(existingAccountancy);
        when(groupRepositoryMock.save(group)).thenReturn(group);
        ObjectAlreadyRegisteredException thrown = assertThrows(ObjectAlreadyRegisteredException.class, () -> {
            groupService.create(group);
        });
        assertTrue(thrown.getMessage().contains(group.getKey()));
        verify(groupRepositoryMock, times(1)).findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void create_invalidAccountancy() {
        Group group = FakerGenerator.fakerGroup();
        when(groupRepositoryMock.findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey())).thenReturn(Optional.empty());
        when(accountancyServiceMock.findOne(group.getAccountancy().getKey())).thenThrow(new ObjectNotFoundException(group.getAccountancy().getKey()));
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            groupService.create(group);
        });
        assertTrue(thrown.getMessage().contains(group.getAccountancy().getKey()));
        verify(groupRepositoryMock, times(1)).findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey());
        verify(accountancyServiceMock, times(1)).findOne(group.getAccountancy().getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void modifyByKey() {
        Group existing = FakerGenerator.fakerGroup();
        Group modified = FakerGenerator.fakerGroup();
        modified.setId(existing.getId());
        modified.setAccountancy(existing.getAccountancy());
        when(groupRepositoryMock.findOne(MOCK_USER, modified.getAccountancy().getKey(), modified.getType(), existing.getKey())).thenReturn(Optional.of(existing));
        when(groupRepositoryMock.save(modified)).thenReturn(modified);
        Group result = groupService.modify(existing.getKey(), modified);
        assertNotNull(result);
        verify(groupRepositoryMock, times(1)).findOne(MOCK_USER, modified.getAccountancy().getKey(), modified.getType(), existing.getKey());
        verify(groupRepositoryMock, times(1)).findOne(MOCK_USER, modified.getAccountancy().getKey(), modified.getType(), modified.getKey());
        verify(groupRepositoryMock, times(1)).save(modified);
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void modifyByKey_notFound() {
        Group existing = FakerGenerator.fakerGroup();
        Group modified = FakerGenerator.fakerGroup();
        modified.setId(existing.getId());
        modified.setAccountancy(existing.getAccountancy());
        when(groupRepositoryMock.findOne(MOCK_USER, modified.getAccountancy().getKey(), modified.getType(), existing.getKey())).thenReturn(Optional.empty());
        String existingGroupKey = existing.getKey();
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            groupService.modify(existingGroupKey, modified);
        });
        assertTrue(thrown.getMessage().contains(existing.getKey()));
        verify(groupRepositoryMock, times(1)).findOne(MOCK_USER, modified.getAccountancy().getKey(), modified.getType(), existing.getKey());
        verify(groupRepositoryMock, times(1)).findOne(MOCK_USER, modified.getAccountancy().getKey(), modified.getType(), modified.getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void deleteByKey() {
        Group group = FakerGenerator.fakerGroup();
        when(groupRepositoryMock.findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey())).thenReturn(Optional.of(group));
        groupService.delete(group.getAccountancy().getKey(), group.getKey(), group.getType());
        verify(groupRepositoryMock, times(1)).findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey());
        verify(groupRepositoryMock, times(1)).delete(group);
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

    @Test
    void deleteByKey_notFound() {
        Group group = FakerGenerator.fakerGroup();
        when(groupRepositoryMock.findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey())).thenReturn(Optional.empty());
        String groupKey = group.getKey();
        String accountancyKey = group.getAccountancy().getKey();
        GroupType groupType = group.getType();
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            groupService.delete(accountancyKey, groupKey, groupType);
        });
        assertTrue(thrown.getMessage().contains(group.getKey()));
        verify(groupRepositoryMock, times(1)).findOne(MOCK_USER, group.getAccountancy().getKey(), group.getType(), group.getKey());
        verifyNoMoreInteractions(groupRepositoryMock, accountancyServiceMock);
    }

}