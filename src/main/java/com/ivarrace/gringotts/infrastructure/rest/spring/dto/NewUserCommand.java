package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserCommand {

  private String username;
  private String password;

}
