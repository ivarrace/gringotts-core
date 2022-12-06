package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.SummaryService;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.AccountancyResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.AccountancyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.Optional;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/accountancy")
public class AccountancySummaryController {

    private final SummaryService summaryService;

    @GetMapping("/{accountancyKey}/summary")
    @PreAuthorize("@accountancyUserRoleChecker.hasPermission(#accountancyKey, T(com.ivarrace.gringotts.domain.accountancy.AccountancyUserRoleType).VIEWER)")
    public ResponseEntity<AccountancyResponse> getSummaryByKey(@PathVariable String accountancyKey,
                                                               @RequestParam Optional<Year> year) {
        AccountancyResponse response = AccountancyMapper.INSTANCE.toResponse(summaryService.getAccountancyWithSummary(accountancyKey, year));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
