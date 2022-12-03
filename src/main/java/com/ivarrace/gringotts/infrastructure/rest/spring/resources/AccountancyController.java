package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.AccountancyService;
import com.ivarrace.gringotts.application.service.ReportService;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.AccountancyResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.CategoryResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.GroupResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.NewAccountancyCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.AccountancyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/accountancy")
public class AccountancyController {

    private final AccountancyService accountancyServer;
    private final ReportService reportService;

    @GetMapping("/")
    public ResponseEntity<List<AccountancyResponse>> getAll() {
        List<AccountancyResponse> response = AccountancyMapper.INSTANCE.toResponseList(accountancyServer.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<AccountancyResponse> save(@Valid @RequestBody NewAccountancyCommand command) {
        AccountancyResponse response = AccountancyMapper.INSTANCE.toResponse(accountancyServer.create(AccountancyMapper.INSTANCE.toDomain(command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{accountancyKey}")
    public ResponseEntity<AccountancyResponse> getByKey(@PathVariable String accountancyKey) {
        AccountancyResponse response = AccountancyMapper.INSTANCE.toResponse(accountancyServer.findByKey(accountancyKey));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{accountancyKey}/summary")
    public ResponseEntity<AccountancyResponse> getSummaryByKey(@PathVariable String accountancyKey) {
        AccountancyResponse response = AccountancyMapper.INSTANCE.toResponse(accountancyServer.findByKey(accountancyKey));
        Integer year = null;
        Integer monthOrdinal = null;
        //TODO move from controller to service
        response.setSummary(reportService.generateByAccountancy(accountancyKey, year, monthOrdinal));
        for (GroupResponse group: response.getExpenses()) {
            group.setSummary(reportService.generateByGroup(accountancyKey, GroupType.EXPENSES, group.getKey(), year, monthOrdinal));
            for(CategoryResponse category: group.getCategories()){
                category.setSummary(reportService.generateByCategory(accountancyKey, GroupType.EXPENSES, group.getKey(), category.getKey(),  year, monthOrdinal));
            }
        }
        for (GroupResponse group: response.getIncomes()) {
            group.setSummary(reportService.generateByGroup(accountancyKey, GroupType.INCOMES, group.getKey(),  year, monthOrdinal));
            for(CategoryResponse category: group.getCategories()){
                category.setSummary(reportService.generateByCategory(accountancyKey, GroupType.INCOMES, group.getKey(), category.getKey(),  year, monthOrdinal));
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{accountancyKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<AccountancyResponse> modify(@PathVariable String accountancyKey,
                                                      @Valid @RequestBody NewAccountancyCommand command) {
        AccountancyResponse response = AccountancyMapper.INSTANCE.toResponse(accountancyServer.modifyByKey(accountancyKey, AccountancyMapper.INSTANCE.toDomain(command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{accountancyKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).OWNER)")
    public ResponseEntity<AccountancyResponse> delete(@PathVariable String accountancyKey) {
        accountancyServer.deleteByKey(accountancyKey);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
