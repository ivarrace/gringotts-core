package com.ivarrace.gringotts.domain.accountancy;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class Movement {

    private String id;
    private LocalDate date;
    private BigDecimal amount;
    private String info;
    private Category category;
}
