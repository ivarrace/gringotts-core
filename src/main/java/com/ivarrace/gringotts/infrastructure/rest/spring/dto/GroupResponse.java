package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GroupResponse {

    private String key;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
    private List<CategoryResponse> categories;
    private AnnualSummaryResponse annualSummary;

}
