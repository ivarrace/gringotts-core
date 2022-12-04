package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.AccountancyService;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.AccountancyResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.NewAccountancyCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.AccountancyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/accountancy")
public class AccountancyController {

    private final AccountancyService accountancyServer;

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
    public ResponseEntity<AccountancyResponse> getSummaryByKey(@PathVariable String accountancyKey,
                                                               @RequestParam Optional<Integer> year) {
        AccountancyResponse response = AccountancyMapper.INSTANCE.toResponse(accountancyServer.findByKeyWIthSummary(accountancyKey, year));
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
