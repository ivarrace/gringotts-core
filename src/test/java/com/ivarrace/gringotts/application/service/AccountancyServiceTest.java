package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.FakerGenerator;
import com.ivarrace.gringotts.application.ports.data.AccountancyRepositoryPort;
import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType;
import com.ivarrace.gringotts.domain.exception.ObjectAlreadyRegisteredException;
import com.ivarrace.gringotts.domain.exception.ObjectNotFoundException;
import com.ivarrace.gringotts.domain.summary.AnnualSummary;
import com.ivarrace.gringotts.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.Year;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@Tag("UnitTest")
class AccountancyServiceTest {

    private static final User MOCK_USER = new User();
    private AccountancyRepositoryPort accountancyRepositoryPortMock;
    private AuthPort authPortMock;

    private AccountancyService accountancyService;

    @BeforeEach
    public void init() {
        accountancyRepositoryPortMock = mock(AccountancyRepositoryPort.class);
        authPortMock = mock(AuthPort.class);
        accountancyService = new AccountancyService(authPortMock, accountancyRepositoryPortMock);
        when(authPortMock.getCurrentUser()).thenReturn(MOCK_USER);
    }

    @Test
    void findAll() {
        accountancyService.findAll();
        verify(accountancyRepositoryPortMock, times(1)).findAll(MOCK_USER);
        verifyNoMoreInteractions(accountancyRepositoryPortMock);
    }

    @Test
    void findByKey() {
        Accountancy accountancy = FakerGenerator.fakerAccountancy();
        when(accountancyRepositoryPortMock.findOne(MOCK_USER, accountancy.getKey())).thenReturn(Optional.of(accountancy));
        Accountancy result = accountancyService.findOne(accountancy.getKey());
        assertNotNull(result);
        assertAll("Expected values", () -> assertEquals(accountancy.getId(), result.getId()),
                () -> assertEquals(accountancy.getKey(), result.getKey()),
                () -> assertEquals(accountancy.getCreatedDate(), result.getCreatedDate()),
                () -> assertEquals(accountancy.getLastModified(), result.getLastModified()),
                () -> assertEquals(accountancy.getName(), result.getName()),
                () -> assertEquals(accountancy.getIncomes().size(), result.getIncomes().size()),
                () -> assertEquals(accountancy.getExpenses().size(), result.getExpenses().size()),
                () -> assertTrue(result.getUsers().isEmpty()));
        verify(accountancyRepositoryPortMock, times(1)).findOne(MOCK_USER, accountancy.getKey());
        verifyNoMoreInteractions(accountancyRepositoryPortMock);
    }

    @Test
    void findByKey_notFound() {
        String accountancyKey = "test";
        when(accountancyRepositoryPortMock.findOne(MOCK_USER, accountancyKey)).thenReturn(Optional.empty());
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            accountancyService.findOne(accountancyKey);
        });
        assertTrue(thrown.getMessage().contains(accountancyKey));
        verify(accountancyRepositoryPortMock, times(1)).findOne(MOCK_USER, accountancyKey);
        verifyNoMoreInteractions(accountancyRepositoryPortMock);
    }

    @Test
    void create() {
        Accountancy accountancy = FakerGenerator.fakerAccountancy();
        when(accountancyRepositoryPortMock.findOne(MOCK_USER, accountancy.getKey())).thenReturn(Optional.empty());
        when(accountancyRepositoryPortMock.save(accountancy)).thenReturn(accountancy);
        Accountancy result = accountancyService.create(accountancy);
        assertNotNull(result);
        assertEquals(1, result.getUsers().size());
        assertAll("Default user values on create", () -> assertEquals(AccountancyUserRoleType.OWNER,
                result.getUsers().get(0).getRole()), () -> assertEquals(MOCK_USER, result.getUsers().get(0).getUser()));
        verify(accountancyRepositoryPortMock, times(1)).findOne(MOCK_USER, accountancy.getKey());
        verify(accountancyRepositoryPortMock, times(1)).save(accountancy);
        verifyNoMoreInteractions(accountancyRepositoryPortMock);
    }

    @Test
    void create_alreadyExists() {
        Accountancy accountancy = FakerGenerator.fakerAccountancy();
        when(accountancyRepositoryPortMock.findOne(MOCK_USER, accountancy.getKey())).thenReturn(Optional.of(accountancy));
        ObjectAlreadyRegisteredException thrown = assertThrows(ObjectAlreadyRegisteredException.class, () -> {
            accountancyService.create(accountancy);
        });
        assertTrue(thrown.getMessage().contains(accountancy.getKey()));
        verify(accountancyRepositoryPortMock, times(1)).findOne(MOCK_USER, accountancy.getKey());
        verifyNoMoreInteractions(accountancyRepositoryPortMock);
    }

    @Test
    void modifyByKey() {
        Accountancy existing = FakerGenerator.fakerAccountancy();
        Accountancy modified = FakerGenerator.fakerAccountancy();
        when(accountancyRepositoryPortMock.findOne(MOCK_USER, existing.getKey())).thenReturn(Optional.of(existing));
        when(accountancyRepositoryPortMock.save(modified)).thenReturn(modified);
        Accountancy result = accountancyService.modify(existing.getKey(), modified);
        assertNotNull(result);
        verify(accountancyRepositoryPortMock, times(1)).findOne(MOCK_USER, existing.getKey());
        verify(accountancyRepositoryPortMock, times(1)).findOne(MOCK_USER, modified.getKey());
        verify(accountancyRepositoryPortMock, times(1)).save(modified);
        verifyNoMoreInteractions(accountancyRepositoryPortMock);
    }

    @Test
    void modifyByKey_notFound() {
        String accountancyKey = "test";
        Accountancy accountancy = new Accountancy();
        accountancy.setKey(accountancyKey);
        when(accountancyRepositoryPortMock.findOne(MOCK_USER, accountancyKey)).thenReturn(Optional.empty());
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            accountancyService.modify(accountancyKey, accountancy);
        });
        assertTrue(thrown.getMessage().contains(accountancyKey));
        verify(accountancyRepositoryPortMock, times(2)).findOne(MOCK_USER, accountancyKey);
        verifyNoMoreInteractions(accountancyRepositoryPortMock);
    }

    @Test
    void deleteByKey() {
        String accountancyKey = "test";
        Accountancy accountancy = new Accountancy();
        accountancy.setKey(accountancyKey);
        when(accountancyRepositoryPortMock.findOne(MOCK_USER, accountancyKey)).thenReturn(Optional.of(accountancy));
        accountancyService.delete(accountancyKey);
        verify(accountancyRepositoryPortMock, times(1)).findOne(MOCK_USER, accountancyKey);
        verify(accountancyRepositoryPortMock, times(1)).delete(accountancy);
        verifyNoMoreInteractions(accountancyRepositoryPortMock);
    }

    @Test
    void deleteByKey_notFound() {
        String accountancyKey = "test";
        when(accountancyRepositoryPortMock.findOne(MOCK_USER, accountancyKey)).thenReturn(Optional.empty());
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            accountancyService.delete(accountancyKey);
        });
        assertTrue(thrown.getMessage().contains(accountancyKey));
        verify(accountancyRepositoryPortMock, times(1)).findOne(MOCK_USER, accountancyKey);
        verifyNoMoreInteractions(accountancyRepositoryPortMock);
    }

}