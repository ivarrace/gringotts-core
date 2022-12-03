package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.MovementService;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.MovementResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.NewMovementCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.UpdateMovementCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/movements")
public class MovementController {

    private final MovementService movementService;

    @GetMapping("/")
    public ResponseEntity<List<MovementResponse>> getAllMovements(@RequestParam(required = false) String accountancyKey,
                                                                  @RequestParam(required = false) String groupKey,
                                                                  @RequestParam(required = false) GroupType groupType,
                                                                  @RequestParam(required = false) String categoryKey,
                                                                  @RequestParam(required = false) Integer monthOrdinal,
                                                                  @RequestParam(required = false) Integer year) {
        //TODO pageable
        List<MovementResponse> response = MovementMapper.INSTANCE.toResponse(movementService.findAll(accountancyKey,groupKey, groupType, categoryKey, monthOrdinal, year));
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
        MovementResponse response = MovementMapper.INSTANCE.toResponse(movementService.findById(movementId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{movementId}")
    public ResponseEntity<MovementResponse> modifyMovement(@PathVariable String movementId,
                                                           @RequestBody UpdateMovementCommand command) {
        MovementResponse response = MovementMapper.INSTANCE.toResponse(movementService.modify(movementId, MovementMapper.INSTANCE.toDomain(command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{movementId}")
    public ResponseEntity<MovementResponse> deleteMovement(@PathVariable String movementId) {
        movementService.delete(movementId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
