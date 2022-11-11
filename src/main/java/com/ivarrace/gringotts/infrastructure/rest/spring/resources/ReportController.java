package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.ReportService;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.ReportResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("")
    public ResponseEntity<ReportResponse> generateReport(@RequestParam(required = false) String accountancyKey,
                                                         @RequestParam(required = false) String groupKey,
                                                         @RequestParam(required = false) GroupType groupType,
                                                         @RequestParam(required = false) String categoryKey) {
        ReportResponse response = ReportMapper.INSTANCE.toResponse(reportService.generateReport(accountancyKey,groupKey, groupType,categoryKey));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
