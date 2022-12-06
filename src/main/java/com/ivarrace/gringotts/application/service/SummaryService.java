package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.domain.accountancy.*;
import com.ivarrace.gringotts.domain.summary.AnnualSummary;
import com.ivarrace.gringotts.domain.summary.MonthSummary;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class SummaryService {

    private final MovementService movementService;

    public SummaryService(MovementService movementService) {
        this.movementService = movementService;
    }

    public Accountancy generateAnnualSummaryForAccountancy(Accountancy accountancy, Optional<Year> year) {
        Year searchByYear = year.orElse(Year.of(LocalDate.now().getYear()));
        List<Movement> accountancyMovements =
                movementService.findAll(accountancy.getKey(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(searchByYear));

        accountancy.setAnnualSummary(generateAnnualSummary(accountancyMovements, searchByYear));
        for(Group group : accountancy.getExpenses()){
            List<Movement> groupMovements = accountancyMovements.stream()
                    .filter(movement -> movement.getCategory().getGroup().getKey().equals(group.getKey()))
                    .collect(Collectors.toList());
            group.setAnnualSummary(generateAnnualSummary(groupMovements, searchByYear));
            for(Category category : group.getCategories()){
                List<Movement> categoryMovements = groupMovements.stream()
                        .filter(movement -> movement.getCategory().getKey().equals(category.getKey()))
                        .collect(Collectors.toList());
                category.setAnnualSummary(generateAnnualSummary(categoryMovements, searchByYear));
            }
        }
        return accountancy;
    }

    private AnnualSummary generateAnnualSummary(List<Movement> movements, Year year){
        double total = movements.stream().mapToDouble(o -> o.getAmount().doubleValue()).sum();
        AnnualSummary annualSummary = new AnnualSummary();
        annualSummary.setYear(year);
        annualSummary.setTotal(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP));
        annualSummary.setAverage(BigDecimal.valueOf(total / Month.values().length).setScale(2, RoundingMode.HALF_UP));
        annualSummary.setMonthly(generateMonthSummary(movements));
        return annualSummary;
    }

    private List<MonthSummary> generateMonthSummary(List<Movement> movements) {
        return Arrays.stream(Month.values()).map(monthItem -> {
                    double monthValues = movements.stream()
                            .filter(movement -> movement.getDate().getMonth().equals(monthItem))
                            .mapToDouble(movement -> movement.getAmount().doubleValue()).sum();
                    return MonthSummary.builder().month(monthItem).total(BigDecimal.valueOf(monthValues).setScale(2, RoundingMode.HALF_UP)).build();
                }).collect(Collectors.toList());
    }
}
