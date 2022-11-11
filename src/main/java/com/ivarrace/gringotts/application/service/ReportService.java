package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.application.repository.MovementRepositoryPort;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.domain.accountancy.Report;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class ReportService {

    private final MovementService movementService;

    public ReportService(MovementService movementService) {
        this.movementService = movementService;
    }

    public Report generateReport(String accountancyKey, String groupKey,
                                 GroupType groupType, String categoryKey) {
        Report report = new Report(); //TODO generate report
        List<Movement> movements = movementService.findAll(accountancyKey, groupKey, groupType, categoryKey);
        report.setCreatedDate(LocalDateTime.now());
        report.setLastModified(LocalDateTime.now());
        report.setMovementsNumber(movements.size());
        return report;
    }
}
