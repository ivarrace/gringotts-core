package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.FakerGenerator;
import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.AccountancyEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataAccountancyRepository;
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
class AccountancyRepositoryAdapterTest {

    private final UUID CURRENT_USER_UUID = UUID.randomUUID();
    private SpringDataAccountancyRepository accountancyRepositoryMock;
    private AccountancyRepositoryAdapter accountancyRepositoryAdapter;
    private User CURRENT_USER;

    @BeforeEach
    public void init() {
        accountancyRepositoryMock = mock(SpringDataAccountancyRepository.class);
        accountancyRepositoryAdapter = new AccountancyRepositoryAdapter(accountancyRepositoryMock);
        CURRENT_USER = new User();
        CURRENT_USER.setId(CURRENT_USER_UUID.toString());
    }

    @Test
    void findAllByUser() {
        AccountancyEntity accountancyEntityExample = FakerGenerator.fakerAccountancyEntity();
        when(accountancyRepositoryMock.findAll(any(Example.class))).thenReturn(Collections.singletonList(accountancyEntityExample));
        List<Accountancy> result = accountancyRepositoryAdapter.findAll(CURRENT_USER);
        assertEquals(1, result.size());
        verify(accountancyRepositoryMock, times(1)).findAll(any(Example.class));
        verifyNoMoreInteractions(accountancyRepositoryMock);
    }

    @Test
    void findByKeyAndUser_empty() {
        AccountancyEntity accountancyEntityExample = FakerGenerator.fakerAccountancyEntity();
        when(accountancyRepositoryMock.findOne(any(Example.class))).thenReturn(Optional.empty());
        Optional<Accountancy> result =
                accountancyRepositoryAdapter.findOne(CURRENT_USER, accountancyEntityExample.getKey());
        assertTrue(result.isEmpty());
        verify(accountancyRepositoryMock, times(1)).findOne(any(Example.class));
        verifyNoMoreInteractions(accountancyRepositoryMock);
    }

    @Test
    void findByKeyAndUser() {
        AccountancyEntity accountancyEntity = FakerGenerator.fakerAccountancyEntity();
        when(accountancyRepositoryMock.findOne(any(Example.class))).thenReturn(Optional.of(accountancyEntity));
        Optional<Accountancy> result =
                accountancyRepositoryAdapter.findOne(CURRENT_USER, accountancyEntity.getKey());
        assertTrue(result.isPresent());
        verify(accountancyRepositoryMock, times(1)).findOne(any(Example.class));
        verifyNoMoreInteractions(accountancyRepositoryMock);
        assertAll("Mapped values",
                () -> assertEquals(accountancyEntity.getId().toString(), result.get().getId()),
                () -> assertEquals(accountancyEntity.getKey(), result.get().getKey()),
                () -> assertEquals(accountancyEntity.getCreatedDate(),
                        result.get().getCreatedDate()),
                () -> assertEquals(accountancyEntity.getLastModified(),
                        result.get().getLastModified()),
                () -> assertEquals(accountancyEntity.getName(), result.get().getName()),
                () -> assertTrue(result.get().getIncomes().isEmpty()),
                () -> assertTrue(result.get().getExpenses().isEmpty()),
                () -> assertTrue(result.get().getUsers().isEmpty())
        );
    }

    @Test
    void save() {
        AccountancyEntity accountancyEntity = FakerGenerator.fakerAccountancyEntity();
        when(accountancyRepositoryMock.save(any())).thenReturn(accountancyEntity);
        Accountancy result = accountancyRepositoryAdapter.save(any());
        verify(accountancyRepositoryMock, times(1)).save(any());
        verifyNoMoreInteractions(accountancyRepositoryMock);
        assertAll("Mapped values",
                () -> assertEquals(accountancyEntity.getId().toString(), result.getId()),
                () -> assertEquals(accountancyEntity.getKey(), result.getKey()),
                () -> assertEquals(accountancyEntity.getCreatedDate(),
                        result.getCreatedDate()),
                () -> assertEquals(accountancyEntity.getLastModified(),
                        result.getLastModified()),
                () -> assertEquals(accountancyEntity.getName(), result.getName()),
                () -> assertTrue(result.getIncomes().isEmpty()),
                () -> assertTrue(result.getExpenses().isEmpty()),
                () -> assertTrue(result.getUsers().isEmpty())
        );
    }

    @Test
    void save_withGroups() {
        AccountancyEntity accountancyEntity = FakerGenerator.fakerAccountancyEntity();
        accountancyEntity.setGroups(List.of(FakerGenerator.fakerGroupEntity(), FakerGenerator.fakerGroupEntity(),
                FakerGenerator.fakerGroupEntity(), FakerGenerator.fakerGroupEntity(),
                FakerGenerator.fakerGroupEntity()));
        when(accountancyRepositoryMock.save(any())).thenReturn(accountancyEntity);
        Accountancy result = accountancyRepositoryAdapter.save(any());
        verify(accountancyRepositoryMock, times(1)).save(any());
        verifyNoMoreInteractions(accountancyRepositoryMock);
        assertAll("Mapped values",
                () -> assertEquals(accountancyEntity.getId().toString(), result.getId()),
                () -> assertEquals(accountancyEntity.getKey(), result.getKey()),
                () -> assertEquals(accountancyEntity.getCreatedDate(),
                        result.getCreatedDate()),
                () -> assertEquals(accountancyEntity.getLastModified(),
                        result.getLastModified()),
                () -> assertEquals(accountancyEntity.getName(), result.getName()),
                () -> assertEquals(
                        accountancyEntity.getGroups().stream()
                                .filter(group -> GroupType.INCOMES.name().equals(group.getType())).count(),
                        result.getIncomes().size()),
                () -> assertEquals(
                        accountancyEntity.getGroups().stream()
                                .filter(group -> GroupType.EXPENSES.name().equals(group.getType())).count(),
                        result.getExpenses().size()),
                () -> assertTrue(result.getUsers().isEmpty())
        );
    }

    @Test
    void delete() {
        accountancyRepositoryAdapter.delete(any());
        verify(accountancyRepositoryMock, times(1)).delete(any());
        verifyNoMoreInteractions(accountancyRepositoryMock);
    }

}