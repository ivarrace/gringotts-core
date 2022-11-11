package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {

  private String id;
  private LocalDateTime createdDate;
  private LocalDateTime lastModified;
  private String username;
  private String authority;

}
