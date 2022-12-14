package com.ivarrace.gringotts.infrastructure.rest.spring.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AccountancyResponse {

    private String key;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
    private String name;
    private List<GroupResponse> incomes;
    private List<GroupResponse> expenses;
    private AnnualSummaryResponse annualSummary;

}
