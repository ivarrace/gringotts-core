package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MovementResponse {

    private String id;
    private LocalDate date;
    private BigDecimal amount;
    private String info;

}
