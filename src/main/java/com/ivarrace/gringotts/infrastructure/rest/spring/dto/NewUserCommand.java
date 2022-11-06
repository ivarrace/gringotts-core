package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import com.ivarrace.gringotts.domain.user.UserAuthority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserCommand {

  private String username;
  private String password;
  private UserAuthority authority;

}
