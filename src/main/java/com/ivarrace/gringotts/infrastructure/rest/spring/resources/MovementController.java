package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.MovementService;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.MovementResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.NewMovementCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.UpdateMovementCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/movements")
public class MovementController {

    private final MovementService movementService;

    @GetMapping("/")
    public ResponseEntity<List<MovementResponse>> getAllMovements(@RequestParam Optional<String> accountancyKey,
                                                                  @RequestParam Optional<String> groupKey,
                                                                  @RequestParam Optional<GroupType> groupType,
                                                                  @RequestParam Optional<String> categoryKey,
                                                                  @RequestParam Optional<Integer> monthOrdinal,
                                                                  @RequestParam Optional<Year> year) {
        //TODO pageable
        Optional<Month> month = monthOrdinal.isPresent() ? Optional.of(Month.of(monthOrdinal.get())) : Optional.empty();
        List<MovementResponse> response =
                MovementMapper.INSTANCE.toResponse(movementService.findAll(accountancyKey, groupKey, groupType,
                        categoryKey, month, year));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<MovementResponse> save(@Valid @RequestBody NewMovementCommand command) {
        MovementResponse response =
                MovementMapper.INSTANCE.toResponse(movementService.create(MovementMapper.INSTANCE.toDomain(command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{movementId}")
    public ResponseEntity<MovementResponse> getMovementById(@PathVariable String movementId) {
        MovementResponse response = MovementMapper.INSTANCE.toResponse(movementService.findOne(movementId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{movementId}")
    public ResponseEntity<MovementResponse> modifyMovement(@PathVariable String movementId,
                                                           @RequestBody UpdateMovementCommand command) {
        MovementResponse response = MovementMapper.INSTANCE.toResponse(movementService.modify(movementId,
                MovementMapper.INSTANCE.toDomain(command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{movementId}")
    public ResponseEntity<MovementResponse> deleteMovement(@PathVariable String movementId) {
        movementService.delete(movementId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
