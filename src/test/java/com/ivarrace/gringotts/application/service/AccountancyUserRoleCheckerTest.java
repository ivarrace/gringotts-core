package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.TestUtils;
import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRole;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;
import com.ivarrace.gringotts.domain.exception.InsufficientPrivilegesException;
import com.ivarrace.gringotts.domain.exception.ObjectNotFoundException;
import com.ivarrace.gringotts.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@Tag("UnitTest")
class AccountancyUserRoleCheckerTest {

    private AccountancyService accountancyServiceMock;
    private AuthPort authPortMock;

    private AccountancyUserRoleChecker accountancyUserRoleChecker;

    @BeforeEach
    public void init() {
        accountancyServiceMock = mock(AccountancyService.class);
        authPortMock = mock(AuthPort.class);
        accountancyUserRoleChecker = new AccountancyUserRoleChecker(accountancyServiceMock, authPortMock);
    }

    @Test
    void hasPermission_VIEWER_request_VIEWER() {
        User user = new User();
        user.setId("testUser");
        Accountancy accountancy = TestUtils.fakerAccountancy();
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(user);
        userRole.setRole(AccountancyUserRoleType.VIEWER);
        accountancy.setUsers(Collections.singletonList(userRole));
        when(authPortMock.getCurrentUser()).thenReturn(user);
        when(accountancyServiceMock.findByKey(accountancy.getKey())).thenReturn(accountancy);
        boolean result = accountancyUserRoleChecker.hasPermission(accountancy.getKey(), AccountancyUserRoleType.VIEWER);
        assertTrue(result);
        verify(accountancyServiceMock, times(1)).findByKey(accountancy.getKey());
        verifyNoMoreInteractions(accountancyServiceMock);
    }

    @Test
    void hasPermission_VIEWER_request_EDITOR() {
        User user = new User();
        user.setId("testUser");
        Accountancy accountancy = TestUtils.fakerAccountancy();
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(user);
        userRole.setRole(AccountancyUserRoleType.VIEWER);
        accountancy.setUsers(Collections.singletonList(userRole));
        when(authPortMock.getCurrentUser()).thenReturn(user);
        when(accountancyServiceMock.findByKey(accountancy.getKey())).thenReturn(accountancy);
        String accountancyKey = accountancy.getKey();
        InsufficientPrivilegesException thrown = assertThrows(InsufficientPrivilegesException.class, () -> {
            accountancyUserRoleChecker.hasPermission(accountancyKey, AccountancyUserRoleType.EDITOR);
        });
        assertTrue(thrown.getMessage().contains(AccountancyUserRoleType.EDITOR.name()));
        verify(accountancyServiceMock, times(1)).findByKey(accountancy.getKey());
        verifyNoMoreInteractions(accountancyServiceMock);
    }

    @Test
    void hasPermission_VIEWER_request_OWNER() {
        User user = new User();
        user.setId("testUser");
        Accountancy accountancy = TestUtils.fakerAccountancy();
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(user);
        userRole.setRole(AccountancyUserRoleType.VIEWER);
        accountancy.setUsers(Collections.singletonList(userRole));
        when(authPortMock.getCurrentUser()).thenReturn(user);
        when(accountancyServiceMock.findByKey(accountancy.getKey())).thenReturn(accountancy);
        String accountancyKey = accountancy.getKey();
        InsufficientPrivilegesException thrown = assertThrows(InsufficientPrivilegesException.class, () -> {
            accountancyUserRoleChecker.hasPermission(accountancyKey, AccountancyUserRoleType.OWNER);
        });
        assertTrue(thrown.getMessage().contains(AccountancyUserRoleType.OWNER.name()));
        verify(accountancyServiceMock, times(1)).findByKey(accountancy.getKey());
        verifyNoMoreInteractions(accountancyServiceMock);
    }

    @Test
    void hasPermission_EDITOR_request_VIEWER() {
        User user = new User();
        user.setId("testUser");
        Accountancy accountancy = TestUtils.fakerAccountancy();
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(user);
        userRole.setRole(AccountancyUserRoleType.EDITOR);
        accountancy.setUsers(Collections.singletonList(userRole));
        when(authPortMock.getCurrentUser()).thenReturn(user);
        when(accountancyServiceMock.findByKey(accountancy.getKey())).thenReturn(accountancy);
        boolean result = accountancyUserRoleChecker.hasPermission(accountancy.getKey(), AccountancyUserRoleType.VIEWER);
        assertTrue(result);
        verify(accountancyServiceMock, times(1)).findByKey(accountancy.getKey());
        verifyNoMoreInteractions(accountancyServiceMock);
    }

    @Test
    void hasPermission_EDITOR_request_EDITOR() {
        User user = new User();
        user.setId("testUser");
        Accountancy accountancy = TestUtils.fakerAccountancy();
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(user);
        userRole.setRole(AccountancyUserRoleType.EDITOR);
        accountancy.setUsers(Collections.singletonList(userRole));
        when(authPortMock.getCurrentUser()).thenReturn(user);
        when(accountancyServiceMock.findByKey(accountancy.getKey())).thenReturn(accountancy);
        boolean result = accountancyUserRoleChecker.hasPermission(accountancy.getKey(), AccountancyUserRoleType.EDITOR);
        assertTrue(result);
        verify(accountancyServiceMock, times(1)).findByKey(accountancy.getKey());
        verifyNoMoreInteractions(accountancyServiceMock);
    }

    @Test
    void hasPermission_EDITOR_request_OWNER() {
        User user = new User();
        user.setId("testUser");
        Accountancy accountancy = TestUtils.fakerAccountancy();
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(user);
        userRole.setRole(AccountancyUserRoleType.EDITOR);
        accountancy.setUsers(Collections.singletonList(userRole));
        when(authPortMock.getCurrentUser()).thenReturn(user);
        when(accountancyServiceMock.findByKey(accountancy.getKey())).thenReturn(accountancy);
        String accountancyKey = accountancy.getKey();
        InsufficientPrivilegesException thrown = assertThrows(InsufficientPrivilegesException.class, () -> {
            accountancyUserRoleChecker.hasPermission(accountancyKey, AccountancyUserRoleType.OWNER);
        });
        assertTrue(thrown.getMessage().contains(AccountancyUserRoleType.OWNER.name()));
        verify(accountancyServiceMock, times(1)).findByKey(accountancy.getKey());
        verifyNoMoreInteractions(accountancyServiceMock);
    }

    @Test
    void hasPermission_OWNER_request_VIEWER() {
        User user = new User();
        user.setId("testUser");
        Accountancy accountancy = TestUtils.fakerAccountancy();
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(user);
        userRole.setRole(AccountancyUserRoleType.OWNER);
        accountancy.setUsers(Collections.singletonList(userRole));
        when(authPortMock.getCurrentUser()).thenReturn(user);
        when(accountancyServiceMock.findByKey(accountancy.getKey())).thenReturn(accountancy);
        boolean result = accountancyUserRoleChecker.hasPermission(accountancy.getKey(), AccountancyUserRoleType.VIEWER);
        assertTrue(result);
        verify(accountancyServiceMock, times(1)).findByKey(accountancy.getKey());
        verifyNoMoreInteractions(accountancyServiceMock);
    }

    @Test
    void hasPermission_OWNER_request_EDITOR() {
        User user = new User();
        user.setId("testUser");
        Accountancy accountancy = TestUtils.fakerAccountancy();
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(user);
        userRole.setRole(AccountancyUserRoleType.OWNER);
        accountancy.setUsers(Collections.singletonList(userRole));
        when(authPortMock.getCurrentUser()).thenReturn(user);
        when(accountancyServiceMock.findByKey(accountancy.getKey())).thenReturn(accountancy);
        boolean result = accountancyUserRoleChecker.hasPermission(accountancy.getKey(), AccountancyUserRoleType.EDITOR);
        assertTrue(result);
        verify(accountancyServiceMock, times(1)).findByKey(accountancy.getKey());
        verifyNoMoreInteractions(accountancyServiceMock);
    }

    @Test
    void hasPermission_OWNER_request_OWNER() {
        User user = new User();
        user.setId("testUser");
        Accountancy accountancy = TestUtils.fakerAccountancy();
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(user);
        userRole.setRole(AccountancyUserRoleType.OWNER);
        accountancy.setUsers(Collections.singletonList(userRole));
        when(authPortMock.getCurrentUser()).thenReturn(user);
        when(accountancyServiceMock.findByKey(accountancy.getKey())).thenReturn(accountancy);
        boolean result = accountancyUserRoleChecker.hasPermission(accountancy.getKey(), AccountancyUserRoleType.OWNER);
        assertTrue(result);
        verify(accountancyServiceMock, times(1)).findByKey(accountancy.getKey());
        verifyNoMoreInteractions(accountancyServiceMock);
    }

    @Test
    void hasPermission_invalidAsset() {
        User user = new User();
        user.setId("testUser");
        Accountancy accountancy = TestUtils.fakerAccountancy();
        AccountancyUserRole userRole = new AccountancyUserRole();
        userRole.setUser(new User());
        userRole.setRole(AccountancyUserRoleType.OWNER);
        accountancy.setUsers(Collections.singletonList(userRole));
        when(authPortMock.getCurrentUser()).thenReturn(user);
        when(accountancyServiceMock.findByKey(accountancy.getKey())).thenReturn(accountancy);
        String accountancyKey = accountancy.getKey();
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            accountancyUserRoleChecker.hasPermission(accountancyKey, AccountancyUserRoleType.OWNER);
        });
        assertTrue(thrown.getMessage().contains(accountancy.getKey()));
        verify(accountancyServiceMock, times(1)).findByKey(accountancy.getKey());
        verifyNoMoreInteractions(accountancyServiceMock);
    }

}