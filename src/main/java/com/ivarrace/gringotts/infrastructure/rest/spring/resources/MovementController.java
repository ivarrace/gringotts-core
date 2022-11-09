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

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/movements")
public class MovementController {

    private final MovementService movementService;

    @GetMapping("/")
    public ResponseEntity<List<MovementResponse>> getAllMovements(@RequestParam(required = false) String accountancyKey,
                                                                  @RequestParam(required = false) String groupKey,
                                                                  @RequestParam(required = false) GroupType groupType,
                                                                  @RequestParam(required = false) String categoryKey) {
        //TODO pageable
        List<MovementResponse> response = MovementMapper.toResponse(movementService.findAll(accountancyKey,groupKey, groupType,categoryKey));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<MovementResponse> save(@RequestBody NewMovementCommand command) {
        MovementResponse response =
                MovementMapper.toResponse(movementService.create(MovementMapper.toDomain(command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{movementId}")
    public ResponseEntity<MovementResponse> getMovementById(@PathVariable String movementId) {
        MovementResponse response = MovementMapper.toResponse(movementService.findById(movementId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{movementId}")
    public ResponseEntity<MovementResponse> modifyMovement(@PathVariable String movementId,
                                                           @RequestBody UpdateMovementCommand command) {
        MovementResponse response = MovementMapper.toResponse(movementService.modify(movementId, MovementMapper.toDomain(command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{movementId}")
    public ResponseEntity<MovementResponse> deleteMovement(@PathVariable String movementId) {
        movementService.delete(movementId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
