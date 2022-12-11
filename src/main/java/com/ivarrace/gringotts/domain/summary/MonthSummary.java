package com.ivarrace.gringotts.domain.summary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Month;

@Getter
@Setter
@Builder
public class MonthSummary {

    private Month month;
    private BigDecimal total;
    private BigDecimal incomes;
    private BigDecimal expenses;
}
