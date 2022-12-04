package com.ivarrace.gringotts.domain.summary;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Getter
@Setter
public class AnnualSummary {

    private Year year;
    private BigDecimal total;
    private BigDecimal average;
    private List<MonthSummary> monthly;
}
