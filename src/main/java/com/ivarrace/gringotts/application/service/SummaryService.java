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
import java.util.stream.Stream;

@Slf4j
public class SummaryService {

    private final AccountancyService accountancyService;
    private final MovementService movementService;

    public SummaryService(AccountancyService accountancyService, MovementService movementService) {
        this.accountancyService = accountancyService;
        this.movementService = movementService;
    }

    public Accountancy getAccountancyWithSummary(String accountancyKey, Optional<Year> year) {
        Accountancy accountancy = accountancyService.findOne(accountancyKey);
        Year searchByYear = year.orElse(Year.of(LocalDate.now().getYear()));
        generateAnnualSummaryForAccountancy(accountancy, searchByYear);
        return accountancy;
    }

    private void generateAnnualSummaryForAccountancy(Accountancy accountancy, Year year) {
        List<Movement> accountancyMovements =
                movementService.findAll(accountancy.getKey(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(year));
        accountancy.setAnnualSummary(generateAnnualSummary(accountancyMovements, year));
        generateAnnualSummaryForGroups(accountancy.getExpenses(), accountancyMovements, year);
        generateAnnualSummaryForGroups(accountancy.getIncomes(), accountancyMovements, year);
    }

    private void generateAnnualSummaryForGroups(List<Group> groupList, List<Movement> movements, Year year) {
        for(Group group : groupList){
            List<Movement> groupMovements = movements.stream()
                    .filter(movement -> movement.getCategory().getGroup().getKey().equals(group.getKey()))
                    .collect(Collectors.toList());
            group.setAnnualSummary(generateAnnualSummary(groupMovements, year));
            generateAnnualSummaryForCategories(group.getCategories(), groupMovements, year);

        }
    }

    private void generateAnnualSummaryForCategories(List<Category> categoryList, List<Movement> groupMovements, Year year) {
        for(Category category : categoryList){
            List<Movement> categoryMovements = groupMovements.stream()
                    .filter(movement -> movement.getCategory().getKey().equals(category.getKey()))
                    .collect(Collectors.toList());
            category.setAnnualSummary(generateAnnualSummary(categoryMovements, year));
        }
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
            List<Movement> monthMovements = movements.stream()
                    .filter(movement -> movement.getDate().getMonth().equals(monthItem))
                    .collect(Collectors.toList());
            double expenses = monthMovements.stream().filter(movement -> GroupType.EXPENSES.equals(movement.getCategory().getGroup().getType())).mapToDouble(movement -> movement.getAmount().doubleValue()).sum();
            double incomes = monthMovements.stream().filter(movement -> GroupType.INCOMES.equals(movement.getCategory().getGroup().getType())).mapToDouble(movement -> movement.getAmount().doubleValue()).sum();
            return MonthSummary.builder()
                    .month(monthItem)
                    .expenses(BigDecimal.valueOf(expenses).setScale(2, RoundingMode.HALF_UP))
                    .incomes(BigDecimal.valueOf(incomes).setScale(2, RoundingMode.HALF_UP))
                    .total(BigDecimal.valueOf(incomes-expenses).setScale(2, RoundingMode.HALF_UP)).build();
        }).collect(Collectors.toList());
    }

}
