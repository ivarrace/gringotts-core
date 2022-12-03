package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class MonthResume {
    private int monthOrdinal;
    private BigDecimal value;

    public MonthResume(int monthOrdinal, double value){
        this.monthOrdinal = monthOrdinal;
        this.value=BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

}
