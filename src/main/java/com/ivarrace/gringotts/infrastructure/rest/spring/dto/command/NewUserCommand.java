package com.ivarrace.gringotts.infrastructure.rest.spring.dto.command;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class NewUserCommand {

  @NotEmpty(message = "name empty value")
  private String username;

  @NotEmpty(message = "name empty value")
  private String password;

}
