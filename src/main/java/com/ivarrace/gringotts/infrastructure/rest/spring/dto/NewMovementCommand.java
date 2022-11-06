package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NewMovementCommand {

  private LocalDate date;
  private double amount;
  private String info;
}
