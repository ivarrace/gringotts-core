package com.ivarrace.gringotts.domain.accountancy;

import com.ivarrace.gringotts.domain.summary.AnnualSummary;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Group {

    private String id;
    private String key;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
    private String name;
    private List<Category> categories;
    private GroupType type;
    private Accountancy accountancy;

    private AnnualSummary annualSummary;
}
