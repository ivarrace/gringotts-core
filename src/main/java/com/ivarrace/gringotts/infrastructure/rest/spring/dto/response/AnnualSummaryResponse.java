package com.ivarrace.gringotts.infrastructure.rest.spring.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Getter
@Setter
public class AnnualSummaryResponse {
    private Year year;
    private BigDecimal total;
    private BigDecimal average;
    private List<MonthSummaryResponse> monthly;
}
