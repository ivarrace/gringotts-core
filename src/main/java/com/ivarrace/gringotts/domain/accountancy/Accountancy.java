package com.ivarrace.gringotts.domain.accountancy;

import com.ivarrace.gringotts.domain.summary.AnnualSummary;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Accountancy {

    private String id;
    private String key;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
    private String name;

    private List<Group> incomes;
    private List<Group> expenses;

    private List<AccountancyUserRole> users;

    private AnnualSummary annualSummary;
}