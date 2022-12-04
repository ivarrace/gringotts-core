package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.CategoryService;
import com.ivarrace.gringotts.application.service.GroupService;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.CategoryResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.GroupResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.NewCategoryCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.NewGroupCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.CategoryMapper;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/accountancy/{accountancyKey}/expenses")
public class ExpensesController {

    private final GroupService groupService;
    private final CategoryService categoryService;

    @GetMapping("/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<List<GroupResponse>> getAllIncomesByAccountancyKey(@PathVariable String accountancyKey) {
        List<GroupResponse> response =
                GroupMapper.INSTANCE.toResponse(groupService.findAll(accountancyKey, GroupType.EXPENSES));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<GroupResponse> save(@PathVariable String accountancyKey, @Valid @RequestBody NewGroupCommand command) {
        GroupResponse response =
                GroupMapper.INSTANCE.toResponse(groupService.create(GroupMapper.INSTANCE.toDomain(command, GroupType.EXPENSES, accountancyKey)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<GroupResponse> getById(@PathVariable String accountancyKey, @PathVariable String groupKey) {
        GroupResponse response =
                GroupMapper.INSTANCE.toResponse(groupService.findOne(groupKey,
                        accountancyKey, GroupType.EXPENSES));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{groupKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<GroupResponse> modify(@PathVariable String accountancyKey, @PathVariable String groupKey,
                                                @Valid @RequestBody NewGroupCommand command) {
        GroupResponse response =
                GroupMapper.INSTANCE.toResponse(
                        groupService.modify(groupKey,
                                GroupMapper.INSTANCE.toDomain(command, GroupType.EXPENSES, accountancyKey)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{groupKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).OWNER)")
    public ResponseEntity<GroupResponse> delete(@PathVariable String accountancyKey, @PathVariable String groupKey) {
        groupService.delete(accountancyKey, groupKey, GroupType.EXPENSES);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{groupKey}/categories/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<List<CategoryResponse>> getIncomeCategories(@PathVariable String accountancyKey, @PathVariable String groupKey) {
        List<CategoryResponse> response =
                CategoryMapper.INSTANCE.toResponse(categoryService.findAll(accountancyKey, GroupType.EXPENSES, groupKey));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{groupKey}/categories/")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<CategoryResponse> saveCategory(@PathVariable String accountancyKey,
                                                         @PathVariable String groupKey,
                                                         @Valid @RequestBody NewCategoryCommand command) {
        CategoryResponse response =
                CategoryMapper.INSTANCE.toResponse(categoryService.create(CategoryMapper.INSTANCE.toDomain(command, groupKey, GroupType.EXPENSES, accountancyKey)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupKey}/categories/{categoryKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable String accountancyKey, @PathVariable String groupKey, @PathVariable String categoryKey) {
        CategoryResponse response =
                CategoryMapper.INSTANCE.toResponse(categoryService.findOne(accountancyKey,GroupType.EXPENSES, groupKey, categoryKey));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{groupKey}/categories/{categoryKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<CategoryResponse> modifyCategory(@PathVariable String accountancyKey,
                                                           @PathVariable String groupKey,
                                                           @PathVariable String categoryKey,
                                                           @Valid @RequestBody NewCategoryCommand command) {
        CategoryResponse response =
                CategoryMapper.INSTANCE.toResponse(
                        categoryService.modify(categoryKey,
                                CategoryMapper.INSTANCE.toDomain(command, groupKey, GroupType.EXPENSES,
                                        accountancyKey)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{groupKey}/categories/{categoryKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey,T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).OWNER)")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable String accountancyKey,
                                                           @PathVariable String groupKey,
                                                           @PathVariable String categoryKey) {
        categoryService.delete(categoryKey, groupKey, GroupType.EXPENSES, accountancyKey);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
