package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Month;

@Getter
@Setter
public class MonthSummaryResponse {
    private Month month;
    private BigDecimal total;

}
