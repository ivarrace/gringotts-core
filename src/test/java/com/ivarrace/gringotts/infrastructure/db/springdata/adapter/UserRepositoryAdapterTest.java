package com.ivarrace.gringotts.infrastructure.db.springdata.adapter;

import com.ivarrace.gringotts.TestUtils;
import com.ivarrace.gringotts.domain.exception.UserAlreadyRegisteredException;
import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.domain.user.UserAuthority;
import com.ivarrace.gringotts.infrastructure.db.springdata.dbo.UserEntity;
import com.ivarrace.gringotts.infrastructure.db.springdata.repository.SpringDataUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@Tag("UnitTest")
class UserRepositoryAdapterTest {

    private SpringDataUserRepository springDataRepositoryMock;
    private BCryptPasswordEncoder bCryptPasswordEncoderMock;

    private UserRepositoryAdapter repositoryAdapter;

    @BeforeEach
    public void init() {
        springDataRepositoryMock = mock(SpringDataUserRepository.class);
        bCryptPasswordEncoderMock = mock(BCryptPasswordEncoder.class);
        repositoryAdapter = new UserRepositoryAdapter(springDataRepositoryMock, bCryptPasswordEncoderMock);
    }

    @Test
    void findByUsername_empty() {
        UserEntity entityExample = TestUtils.fakerUserEntity();
        when(springDataRepositoryMock.findByUsername(entityExample.getUsername())).thenReturn(Optional.empty());
        Optional<User> result = repositoryAdapter.findByUsername(entityExample.getUsername());
        assertTrue(result.isEmpty());
        verify(springDataRepositoryMock, times(1)).findByUsername(entityExample.getUsername());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void findByUsername() {
        UserEntity entityExample = TestUtils.fakerUserEntity();
        when(springDataRepositoryMock.findByUsername(entityExample.getUsername())).thenReturn(Optional.of(entityExample));
        Optional<User> result = repositoryAdapter.findByUsername(entityExample.getUsername());
        assertTrue(result.isPresent());
        verify(springDataRepositoryMock, times(1)).findByUsername(entityExample.getUsername());
        verifyNoMoreInteractions(springDataRepositoryMock);
    }

    @Test
    void save_fail() {
        UserEntity entityExample = TestUtils.fakerUserEntity();
        String encodedPwd = "secret";
        when(springDataRepositoryMock.findByUsername(entityExample.getUsername())).thenReturn(Optional.of(entityExample));
        User toSave = new User();
        toSave.setUsername(entityExample.getUsername());
        UserAlreadyRegisteredException thrown = assertThrows(UserAlreadyRegisteredException.class, () -> {
            User result = repositoryAdapter.save(toSave);
        });
        assertTrue(thrown.getMessage().contains(toSave.getUsername()));
        verify(springDataRepositoryMock, times(1)).findByUsername(entityExample.getUsername());
        verify(springDataRepositoryMock, times(0)).save(any());
        verify(bCryptPasswordEncoderMock, times(0)).encode(entityExample.getPassword());
        verifyNoMoreInteractions(springDataRepositoryMock, bCryptPasswordEncoderMock);
    }

    @Test
    void save() {
        UserEntity entityExample = TestUtils.fakerUserEntity();
        String encodedPwd = "secret";
        when(springDataRepositoryMock.findByUsername(entityExample.getUsername())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoderMock.encode(entityExample.getPassword())).thenReturn(encodedPwd);
        when(springDataRepositoryMock.save(any())).thenReturn(entityExample);
        User toSave = new User();
        toSave.setUsername(entityExample.getUsername());
        toSave.setAuthority(UserAuthority.valueOf(entityExample.getAuthority()));
        toSave.setPassword(encodedPwd);
        User result = repositoryAdapter.save(toSave);
        assertNotNull(result);
        verify(springDataRepositoryMock, times(1)).findByUsername(entityExample.getUsername());
        verify(springDataRepositoryMock, times(1)).save(any());
        verify(bCryptPasswordEncoderMock, times(1)).encode(toSave.getPassword());
        verifyNoMoreInteractions(springDataRepositoryMock, bCryptPasswordEncoderMock);
    }
}