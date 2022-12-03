package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import com.ivarrace.gringotts.domain.accountancy.GroupType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class NewMovementCommand {

  @NotEmpty(message = "accountancyKey empty value")
  private String accountancyKey;

  @NotNull(message = "groupType empty value")
  private GroupType groupType;

  @NotEmpty(message = "groupKey empty value")
  private String groupKey;

  @NotEmpty(message = "categoryKey empty value")
  private String categoryKey;

  @NotNull(message = "date empty value")
  private LocalDate date;

  @NotNull(message = "amount empty value")
  private BigDecimal amount;

  private String info;
}
