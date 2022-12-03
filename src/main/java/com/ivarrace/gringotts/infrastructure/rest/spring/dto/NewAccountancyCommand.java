package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class NewAccountancyCommand {

  @NotEmpty(message = "name empty value")
  private String name;

}
