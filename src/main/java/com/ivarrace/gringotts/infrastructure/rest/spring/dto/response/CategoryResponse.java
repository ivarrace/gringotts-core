package com.ivarrace.gringotts.infrastructure.rest.spring.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryResponse {

    private String key;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
    private AnnualSummaryResponse annualSummary;

}
