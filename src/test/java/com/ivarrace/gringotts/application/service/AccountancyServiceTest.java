package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.ports.AuthPort;
import com.ivarrace.gringotts.application.repository.AccountancyRepositoryPort;
import com.ivarrace.gringotts.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

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
        accountancyService = new AccountancyService(accountancyRepositoryPortMock, authPortMock);
        when(authPortMock.getCurrentUser()).thenReturn(MOCK_USER);
    }

    @Test
    void findAll() {
        accountancyService.findAll();
        verify(accountancyRepositoryPortMock, times(1)).findAllByUser(MOCK_USER);
        verifyNoMoreInteractions(accountancyRepositoryPortMock);
    }

}