package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateMovementCommand {

  @NotEmpty(message = "date empty value")
  private LocalDate date;

  @NotEmpty(message = "amount empty value")
  private BigDecimal amount;

  private String info;
}
