package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.domain.accountancy.Movement;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.AnnualSummary;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.MonthResume;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ReportService {

    private final MovementService movementService;

    public ReportService(MovementService movementService) {
        this.movementService = movementService;
    }

    public AnnualSummary generateByAccountancy(String accountancyKey, Integer year, Integer monthOrdinal) {
        return generateByCategory(accountancyKey, null, null, null, year, monthOrdinal);
    }

    public AnnualSummary generateByGroup(String accountancyKey, GroupType groupType, String groupKey, Integer year,
                                         Integer monthOrdinal) {
        return generateByCategory(accountancyKey, groupType, groupKey, null, year, monthOrdinal);
    }

    public AnnualSummary generateByCategory(String accountancyKey, GroupType groupType, String groupKey,
                                            String categoryKey, Integer year, Integer monthOrdinal) {
        List<Movement> movements =
                movementService.findAll(accountancyKey, groupKey, groupType, categoryKey, monthOrdinal, year);

        List<MonthResume> monthResumeList =
                Arrays.stream(Month.values()).map(month -> {
                    double monthValues = movements.stream()
                            .filter(movement -> movement.getDate().getMonth().equals(month))
                            .mapToDouble(movement -> movement.getAmount().doubleValue()).sum();
                    return new MonthResume(month.getValue(), monthValues);
                }).collect(Collectors.toList());

        AnnualSummary annualSummary = new AnnualSummary();
        double total = movements.stream().mapToDouble(o -> o.getAmount().doubleValue()).sum();
        annualSummary.setTotal(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP));
        annualSummary.setAverage(BigDecimal.valueOf(total / 12).setScale(2, RoundingMode.HALF_UP));
        annualSummary.setMonthly(monthResumeList);
        return annualSummary;
    }
}
