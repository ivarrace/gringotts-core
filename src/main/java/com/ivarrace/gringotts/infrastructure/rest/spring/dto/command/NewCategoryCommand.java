package com.ivarrace.gringotts.infrastructure.rest.spring.dto.command;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class NewCategoryCommand {

  @NotEmpty(message = "name empty value")
  private String name;

}
