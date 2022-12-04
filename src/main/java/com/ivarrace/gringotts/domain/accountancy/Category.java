package com.ivarrace.gringotts.domain.accountancy;

import com.ivarrace.gringotts.domain.summary.AnnualSummary;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Category {

    private String id;
    private String key;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
    private String name;
    private Group group;

    private AnnualSummary annualSummary;
}
