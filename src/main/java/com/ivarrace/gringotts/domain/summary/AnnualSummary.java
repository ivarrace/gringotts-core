package com.ivarrace.gringotts.domain.summary;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AnnualSummary {

    private int year;
    private BigDecimal total;
    private BigDecimal average;
    private List<MonthSummary> monthly;
}
