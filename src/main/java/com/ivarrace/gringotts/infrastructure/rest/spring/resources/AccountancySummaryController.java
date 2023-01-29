package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.SummaryService;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.AccountancyResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.AccountancyMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Summary", description = "Retrieve summary information")
@SecurityRequirement(name = "bearerAuth")
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
