package com.ivarrace.gringotts.application.service;

import com.ivarrace.gringotts.domain.accountancy.*;
import com.ivarrace.gringotts.domain.summary.AnnualSummary;
import com.ivarrace.gringotts.domain.summary.MonthSummary;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
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
//TODO use month and year from java.time
    private AnnualSummary generate(Optional<String> accountancyKey, Optional<GroupType> groupType, Optional<String> groupKey,
                                  Optional<String> categoryKey, Integer year, Optional<Month> month) {
        List<Movement> movements =
                movementService.findAll(accountancyKey, groupKey, groupType, categoryKey, month, Optional.of(year));

        List<MonthSummary> monthResumeList =
                Arrays.stream(Month.values()).map(monthItem -> {
                    double monthValues = movements.stream()
                            .filter(movement -> movement.getDate().getMonth().equals(monthItem))
                            .mapToDouble(movement -> movement.getAmount().doubleValue()).sum();
                    return MonthSummary.builder().month(monthItem).total(BigDecimal.valueOf(monthValues).setScale(2, RoundingMode.HALF_UP)).build();
                }).collect(Collectors.toList());

        AnnualSummary annualSummary = new AnnualSummary();
        double total = movements.stream().mapToDouble(o -> o.getAmount().doubleValue()).sum();
        annualSummary.setYear(year);
        annualSummary.setTotal(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP));
        annualSummary.setAverage(BigDecimal.valueOf(total / 12).setScale(2, RoundingMode.HALF_UP));
        annualSummary.setMonthly(monthResumeList);
        return annualSummary;
    }

    public Accountancy generateAnnualSummaryForAccountancy(Accountancy accountancy, Optional<Integer> optionalYear) {
        //TODO gel all movements an filter with stream
        Integer searchByYear = optionalYear.orElse(LocalDate.now().getYear());
        accountancy.setAnnualSummary(this.generate(Optional.of(accountancy.getKey()), Optional.empty(),Optional.empty(),Optional.empty(),searchByYear,Optional.empty()));
        for (Group group: accountancy.getExpenses()) {
            group.setAnnualSummary(this.generate(Optional.of(accountancy.getKey()), Optional.of(GroupType.EXPENSES), Optional.of(group.getKey()), Optional.empty(), searchByYear,Optional.empty()));
            for(Category category: group.getCategories()){
                category.setAnnualSummary(this.generate(Optional.of(accountancy.getKey()), Optional.of(GroupType.EXPENSES), Optional.of(group.getKey()), Optional.of(category.getKey()), searchByYear,Optional.empty()));
            }
        }
        for (Group group: accountancy.getIncomes()) {
            group.setAnnualSummary(this.generate(Optional.of(accountancy.getKey()), Optional.of(GroupType.INCOMES), Optional.of(group.getKey()), Optional.empty(), searchByYear,Optional.empty()));
            for(Category category: group.getCategories()){
                category.setAnnualSummary(this.generate(Optional.of(accountancy.getKey()), Optional.of(GroupType.EXPENSES), Optional.of(group.getKey()), Optional.of(category.getKey()), searchByYear,Optional.empty()));
            }
        }
        return accountancy;
    }
}
