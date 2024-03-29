package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.AccountancyService;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.NewAccountancyCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.AccountancyResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.AccountancyMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/accountancy")
@Slf4j
@Tag(name = "Accountancy management", description = "Manage accountancy properties and retrieve accountancy information")
@SecurityRequirement(name = "bearerAuth")
public class AccountancyController {

    private final AccountancyService accountancyServer;

    @Operation(summary = "Get all user accountancies")
    @GetMapping("/")
    public ResponseEntity<List<AccountancyResponse>> getAll() {
        List<AccountancyResponse> response = AccountancyMapper.INSTANCE.toResponseList(accountancyServer.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Add new accountancy")
    @PostMapping("")
    public ResponseEntity<AccountancyResponse> save(@Valid @RequestBody NewAccountancyCommand command) {
        AccountancyResponse response = AccountancyMapper.INSTANCE.toResponse(accountancyServer.create(AccountancyMapper.INSTANCE.toDomain(command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get accountancy by key")
    @GetMapping("/{accountancyKey}")
    public ResponseEntity<AccountancyResponse> getByKey(@PathVariable String accountancyKey) {
        AccountancyResponse response = AccountancyMapper.INSTANCE.toResponse(accountancyServer.findOne(accountancyKey));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{accountancyKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).EDITOR)")
    public ResponseEntity<AccountancyResponse> modify(@PathVariable String accountancyKey,
                                                      @Valid @RequestBody NewAccountancyCommand command) {
        AccountancyResponse response = AccountancyMapper.INSTANCE.toResponse(accountancyServer.modify(accountancyKey, AccountancyMapper.INSTANCE.toDomain(command)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{accountancyKey}")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).OWNER)")
    public ResponseEntity<AccountancyResponse> delete(@PathVariable String accountancyKey) {
        accountancyServer.delete(accountancyKey);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
