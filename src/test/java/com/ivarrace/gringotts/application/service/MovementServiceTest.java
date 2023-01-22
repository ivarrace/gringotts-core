package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.FakerGenerator;
import com.ivarrace.gringotts.application.ports.data.MovementRepositoryPort;
import com.ivarrace.gringotts.application.ports.security.AuthPort;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.domain.exception.ObjectNotFoundException;
import com.ivarrace.gringotts.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.Month;
import java.time.Year;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@Tag("UnitTest")
class MovementServiceTest {

    private static final User MOCK_USER = new User();
    private AuthPort authPort;
    private CategoryService categoryService;
    private MovementRepositoryPort movementRepositoryPort;

    private MovementService movementService;

    @BeforeEach
    public void init() {
        authPort = mock(AuthPort.class);
        categoryService = mock(CategoryService.class);
        movementRepositoryPort = mock(MovementRepositoryPort.class);
        movementService = new MovementService(authPort, categoryService, movementRepositoryPort);
        when(authPort.getCurrentUser()).thenReturn(MOCK_USER);
    }

    @Test
    void findAll() {
        String accountancyKey = "test-key";
        Optional<String> groupKey = Optional.empty();
        Optional<GroupType> groupType = Optional.empty();
        Optional<String> categoryKey = Optional.empty();
        Optional<Month> month = Optional.empty();
        Optional<Year> year = Optional.empty();
        movementService.findAll(accountancyKey, groupKey, groupType, categoryKey, month, year);
        verify(movementRepositoryPort, times(1))
                .findAll(accountancyKey, groupType, groupKey, categoryKey, month, year, MOCK_USER);
        verifyNoMoreInteractions(movementRepositoryPort);
    }

    @Test
    void findOne() {
        Movement existingMovement = FakerGenerator.fakerMovement();
        when(movementRepositoryPort.findById(existingMovement.getId())).thenReturn(Optional.of(existingMovement));
        Movement result = movementService.findOne(existingMovement.getId());
        assertNotNull(result);
        assertAll("Expected values", () -> assertEquals(existingMovement.getId(), result.getId()),
                () -> assertEquals(existingMovement.getDate(), result.getDate()),
                () -> assertEquals(existingMovement.getCategory(), result.getCategory()),
                () -> assertEquals(existingMovement.getAmount(), result.getAmount()),
                () -> assertEquals(existingMovement.getInfo(), result.getInfo()));
        verify(movementRepositoryPort, times(1))
                .findById(existingMovement.getId());
        verifyNoMoreInteractions(movementRepositoryPort);
    }

    @Test
    void findOne_notFound() {
        String movementId = "test";
        when(movementRepositoryPort.findById(movementId)).thenReturn(Optional.empty());
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            movementService.findOne(movementId);
        });
        assertTrue(thrown.getMessage().contains(movementId));
        verify(movementRepositoryPort, times(1))
                .findById(movementId);
        verifyNoMoreInteractions(movementRepositoryPort);
    }

    @Test
    void create() {
        Movement movement = FakerGenerator.fakerMovement();
        when(categoryService.findOne(
                movement.getCategory().getGroup().getAccountancy().getKey(),
                movement.getCategory().getGroup().getType(),
                movement.getCategory().getGroup().getKey(),
                movement.getCategory().getKey())).thenReturn(movement.getCategory());
        when(movementRepositoryPort.save(movement)).thenReturn(movement);
        Movement result = movementService.create(movement.getCategory().getGroup().getAccountancy().getKey(), movement);
        assertNotNull(result);
        verify(categoryService, times(1)).findOne(
                movement.getCategory().getGroup().getAccountancy().getKey(),
                movement.getCategory().getGroup().getType(),
                movement.getCategory().getGroup().getKey(),
                movement.getCategory().getKey());
        verify(movementRepositoryPort, times(1)).save(any());
        verifyNoMoreInteractions(categoryService, movementRepositoryPort);
    }

    @Test
    void create_alreadyExists() {
        Movement movement = FakerGenerator.fakerMovement();
        when(categoryService.findOne(
                movement.getCategory().getGroup().getAccountancy().getKey(),
                movement.getCategory().getGroup().getType(),
                movement.getCategory().getGroup().getKey(),
                movement.getCategory().getKey())).thenThrow(new ObjectNotFoundException(movement.getCategory().getKey()));
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            movementService.create(movement.getCategory().getGroup().getAccountancy().getKey(), movement);
        });
        assertTrue(thrown.getMessage().contains(movement.getCategory().getKey()));
        verify(categoryService, times(1)).findOne(
                movement.getCategory().getGroup().getAccountancy().getKey(),
                movement.getCategory().getGroup().getType(),
                movement.getCategory().getGroup().getKey(),
                movement.getCategory().getKey());
        verify(movementRepositoryPort, times(0)).save(any());
        verifyNoMoreInteractions(categoryService, movementRepositoryPort);
    }

    @Test
    void modify() {
        Movement existingMovement = FakerGenerator.fakerMovement();
        Movement updatedMovement = FakerGenerator.fakerMovement();
        updatedMovement.setId(existingMovement.getId());
        updatedMovement.setCategory(existingMovement.getCategory());
        when(movementRepositoryPort.findById(existingMovement.getId())).thenReturn(Optional.of(existingMovement));
        when(movementRepositoryPort.save(updatedMovement)).thenReturn(updatedMovement);
        Movement result = movementService.modify(existingMovement.getId(), updatedMovement);
        assertNotNull(result);
        verify(movementRepositoryPort, times(1)).findById(existingMovement.getId());
        verify(movementRepositoryPort, times(1)).save(updatedMovement);
        verifyNoMoreInteractions(movementRepositoryPort);
    }

    @Test
    void modify_notFound() {
        Movement existingMovement = FakerGenerator.fakerMovement();
        Movement updatedMovement = FakerGenerator.fakerMovement();
        when(movementRepositoryPort.findById(existingMovement.getId())).thenThrow(new ObjectNotFoundException(existingMovement.getId()));
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            movementService.modify(existingMovement.getId(), updatedMovement);
        });

        assertTrue(thrown.getMessage().contains(existingMovement.getId()));
        verify(movementRepositoryPort, times(1)).findById(existingMovement.getId());
        verify(movementRepositoryPort, times(0)).save(any());
        verifyNoMoreInteractions(movementRepositoryPort);
    }

    @Test
    void delete() {
        Movement existingMovement = FakerGenerator.fakerMovement();
        when(movementRepositoryPort.findById(existingMovement.getId())).thenReturn(Optional.of(existingMovement));
        movementService.delete(existingMovement.getId());
        verify(movementRepositoryPort, times(1)).findById(existingMovement.getId());
        verify(movementRepositoryPort, times(1)).delete(existingMovement);
        verifyNoMoreInteractions(movementRepositoryPort);
    }

    @Test
    void deleteByKey_notFound() {
        String existingMovementId = "test-id";
        when(movementRepositoryPort.findById(existingMovementId)).thenThrow(new ObjectNotFoundException(existingMovementId));
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            movementService.delete(existingMovementId);
        });

        assertTrue(thrown.getMessage().contains(existingMovementId));
        verify(movementRepositoryPort, times(1)).findById(existingMovementId);
        verify(movementRepositoryPort, times(0)).delete(any());
        verifyNoMoreInteractions(movementRepositoryPort);
    }

}