package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.CategoryService;
import com.ivarrace.gringotts.application.service.GroupService;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.CategoryResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.GroupResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.NewCategoryCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.NewGroupCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.CategoryMapper;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/accountancy/{accountancyKey}/expenses")
public class ExpensesController {

    private final GroupService groupService;
    private final CategoryService categoryService;

    @GetMapping("/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<List<GroupResponse>> getAllIncomesByAccountancyKey(@PathVariable String accountancyKey) {
        List<GroupResponse> response =
                GroupMapper.toResponse(groupService.findByAccountancyKeyAndType(accountancyKey, GroupType.EXPENSES));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<GroupResponse> save(@PathVariable String accountancyKey, @RequestBody NewGroupCommand command) {
        GroupResponse response =
                GroupMapper.toResponse(groupService.create(GroupMapper.toDomain(command, GroupType.EXPENSES, accountancyKey)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<GroupResponse> getById(@PathVariable String accountancyKey, @PathVariable String groupKey) {
        GroupResponse response =
                GroupMapper.toResponse(groupService.findByKey(groupKey,
                        accountancyKey, GroupType.EXPENSES));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{groupKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<GroupResponse> modify(@PathVariable String accountancyKey, @PathVariable String groupKey,
                                                @RequestBody NewGroupCommand command) {
        GroupResponse response =
                GroupMapper.toResponse(
                        groupService.modify(groupKey,
                                GroupMapper.toDomain(command, GroupType.EXPENSES,
                                        accountancyKey)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{groupKey}") //TODO delete cascade
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).OWNER)")
    public ResponseEntity<GroupResponse> delete(@PathVariable String accountancyKey, @PathVariable String groupKey) {
        groupService.delete(accountancyKey, groupKey, GroupType.EXPENSES);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{groupKey}/categories/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<List<CategoryResponse>> getIncomeCategories(@PathVariable String accountancyKey, @PathVariable String groupKey) {
        List<CategoryResponse> response =
                CategoryMapper.toResponse(categoryService.findAllInGroup(groupKey));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{groupKey}/categories/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<CategoryResponse> saveCategory(@PathVariable String accountancyKey,
                                                         @PathVariable String groupKey,
                                                         @RequestBody NewCategoryCommand command) {
        CategoryResponse response =
                CategoryMapper.toResponse(categoryService.create(CategoryMapper.toDomain(command, GroupType.EXPENSES, accountancyKey, groupKey)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupKey}/categories/{categoryKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable String accountancyKey, @PathVariable String groupKey, @PathVariable String categoryKey) {
        CategoryResponse response =
                CategoryMapper.toResponse(categoryService.findByKeyInGroup(categoryKey, groupKey, GroupType.EXPENSES, accountancyKey));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{groupKey}/categories/{categoryKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<CategoryResponse> modifyCategory(@PathVariable String accountancyKey,
                                                           @PathVariable String groupKey,
                                                           @PathVariable String categoryKey,
                                                           @RequestBody NewCategoryCommand command) {
        CategoryResponse response =
                CategoryMapper.toResponse(
                        categoryService.modify(categoryKey,
                                CategoryMapper.toDomain(command, GroupType.EXPENSES,
                                        accountancyKey, groupKey)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{groupKey}/categories/{categoryKey}") //TODO delete cascade
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).OWNER)")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable String accountancyKey,
                                                           @PathVariable String groupKey,
                                                           @PathVariable String categoryKey) {
        categoryService.delete(categoryKey, groupKey, GroupType.EXPENSES, accountancyKey);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
