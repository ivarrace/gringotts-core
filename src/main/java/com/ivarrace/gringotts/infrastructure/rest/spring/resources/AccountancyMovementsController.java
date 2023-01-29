package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.MovementService;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.NewMovementCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.UpdateMovementCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.MovementResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.MovementMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/accountancy/{accountancyKey}/movements")
@Tag(name = "Movements", description = "Manage movements")
@SecurityRequirement(name = "bearerAuth")
public class AccountancyMovementsController {

    private final MovementService movementService;

    @GetMapping("/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<List<MovementResponse>> getAllMovements(@PathVariable String accountancyKey,
                                                                  @RequestParam Optional<String> groupKey,
                                                                  @RequestParam Optional<GroupType> groupType,
                                                                  @RequestParam Optional<String> categoryKey,
                                                                  @RequestParam Optional<Integer> monthOrdinal,
                                                                  @RequestParam Optional<Year> year) {
        Optional<Month> month = monthOrdinal.isPresent() ? Optional.of(Month.of(monthOrdinal.get())) : Optional.empty();
        List<MovementResponse> response =
                MovementMapper.INSTANCE.toResponse(movementService.findAll(accountancyKey, groupKey, groupType,
                        categoryKey, month, year));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<MovementResponse> save(@PathVariable String accountancyKey,
                                                 @Valid @RequestBody NewMovementCommand command) {
        MovementResponse response =
                MovementMapper.INSTANCE.toResponse(movementService.create(accountancyKey, MovementMapper.INSTANCE.toDomain(command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{movementId}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<MovementResponse> getMovementById(@PathVariable String accountancyKey,
                                                            @PathVariable String movementId) {
        MovementResponse response = MovementMapper.INSTANCE.toResponse(movementService.findOne(movementId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{movementId}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<MovementResponse> modifyMovement(@PathVariable String accountancyKey,
                                                           @PathVariable String movementId,
                                                           @RequestBody UpdateMovementCommand command) {
        MovementResponse response = MovementMapper.INSTANCE.toResponse(movementService.modify(movementId,
                MovementMapper.INSTANCE.toDomain(command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{movementId}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<MovementResponse> deleteMovement(@PathVariable String accountancyKey,
                                                           @PathVariable String movementId) {
        movementService.delete(movementId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
