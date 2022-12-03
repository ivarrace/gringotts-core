package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AnnualSummary {
    private BigDecimal total;
    private BigDecimal average;
    private List<MonthResume> monthly;
}
