package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import com.ivarrace.gringotts.domain.accountancy.GroupType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NewMovementCommand {

  private String accountancyKey;
  private GroupType groupType;
  private String groupKey;
  private String categoryKey;
  private LocalDate date;
  private double amount;
  private String info;
}
