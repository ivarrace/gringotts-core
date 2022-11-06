package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.CategoryService;
import com.ivarrace.gringotts.application.service.GroupService;
import com.ivarrace.gringotts.application.service.MovementService;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.*;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.CategoryMapper;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.GroupMapper;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/accountancy/{accountancyKey}/incomes")
public class IncomesController {

    private static final GroupType GROUP_TYPE = GroupType.INCOMES;

    private final GroupService groupService;
    private final CategoryService categoryService;
    private final MovementService movementService;

    @GetMapping("/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<List<GroupResponse>> getAllIncomesByAccountancyKey(@PathVariable String accountancyKey) {
        List<GroupResponse> response = GroupMapper.toResponse(groupService.findByAccountancyKeyAndType(accountancyKey, GROUP_TYPE));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<GroupResponse> save(@PathVariable String accountancyKey, @RequestBody NewGroupCommand command) {
        GroupResponse response =
                GroupMapper.toResponse(groupService.create(GroupMapper.toDomain(command, GROUP_TYPE, accountancyKey)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<GroupResponse> getById(@PathVariable String accountancyKey, @PathVariable String groupKey) {
        GroupResponse response =
                GroupMapper.toResponse(groupService.findByKey(groupKey, accountancyKey, GROUP_TYPE));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{groupKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<GroupResponse> modify(@PathVariable String accountancyKey, @PathVariable String groupKey,
                                                @RequestBody NewGroupCommand command) {
        GroupResponse response =
                GroupMapper.toResponse(
                        groupService.modify(groupKey,
                                GroupMapper.toDomain(command, GROUP_TYPE, accountancyKey)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{groupKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).OWNER)")
    public ResponseEntity<GroupResponse> delete(@PathVariable String accountancyKey, @PathVariable String groupKey) {
        groupService.delete(accountancyKey, groupKey, GROUP_TYPE);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{groupKey}/categories/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<List<CategoryResponse>> getIncomeCategories(@PathVariable String accountancyKey, @PathVariable String groupKey) {
        List<CategoryResponse> response =
                CategoryMapper.toResponse(categoryService.findAllInGroup(groupKey));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{groupKey}/categories/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<CategoryResponse> saveCategory(@PathVariable String accountancyKey,
                                                         @PathVariable String groupKey,
                                                         @RequestBody NewCategoryCommand command) {
        CategoryResponse response =
                CategoryMapper.toResponse(categoryService.create(CategoryMapper.toDomain(command, GROUP_TYPE, accountancyKey, groupKey)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupKey}/categories/{categoryKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable String accountancyKey, @PathVariable String groupKey, @PathVariable String categoryKey) {
        CategoryResponse response =
                CategoryMapper.toResponse(categoryService.findByKeyInGroup(categoryKey, groupKey));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{groupKey}/categories/{categoryKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<CategoryResponse> modifyCategory(@PathVariable String accountancyKey,
                                                           @PathVariable String groupKey,
                                                           @PathVariable String categoryKey,
                                                           @RequestBody NewCategoryCommand command) {
        CategoryResponse response =
                CategoryMapper.toResponse(
                        categoryService.modify(categoryKey, CategoryMapper.toDomain(command, GROUP_TYPE, accountancyKey, groupKey)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{groupKey}/categories/{categoryKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).OWNER)")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable String accountancyKey,
                                                           @PathVariable String groupKey,
                                                           @PathVariable String categoryKey) {
        categoryService.delete(categoryKey, groupKey);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{groupKey}/categories/{categoryKey}/movements")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<List<MovementResponse>> getAllMovements(@PathVariable String accountancyKey,
                                                                  @PathVariable String groupKey,
                                                                  @PathVariable String categoryKey) {
        List<MovementResponse> response = MovementMapper.toResponse(movementService.findAll(categoryKey));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{groupKey}/categories/{categoryKey}/movements")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<MovementResponse> save(@PathVariable String accountancyKey,
                                                 @PathVariable String groupKey,
                                                 @PathVariable String categoryKey,
                                                 @RequestBody NewMovementCommand command) {
        MovementResponse response =
                MovementMapper.toResponse(movementService.create(MovementMapper.toDomain(accountancyKey, GROUP_TYPE, groupKey, categoryKey, command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupKey}/categories/{categoryKey}/movements/{movementId}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<MovementResponse> getMovementById(@PathVariable String accountancyKey,
                                                            @PathVariable String groupKey,
                                                            @PathVariable String categoryKey,
                                                            @PathVariable String movementId) {
        MovementResponse response = MovementMapper.toResponse(movementService.findById(movementId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{groupKey}/categories/{categoryKey}/movements/{movementId}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<MovementResponse> modifyMovement(@PathVariable String accountancyKey,
                                                           @PathVariable String groupKey,
                                                           @PathVariable String categoryKey,
                                                           @PathVariable String movementId,
                                                           @RequestBody NewMovementCommand command) {
        MovementResponse response = MovementMapper.toResponse(movementService.modify(movementId, MovementMapper.toDomain(accountancyKey, GROUP_TYPE, groupKey, categoryKey, command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{groupKey}/categories/{categoryKey}/movements/{movementId}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<MovementResponse> deleteMovement(@PathVariable String accountancyKey,
                                                           @PathVariable String groupKey,
                                                           @PathVariable String categoryKey,
                                                           @PathVariable String movementId) {
        movementService.delete(movementId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
