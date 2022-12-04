package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AnnualSummaryResponse {
    private Integer year;
    private BigDecimal total;
    private BigDecimal average;
    private List<MonthSummaryResponse> monthly;
}
