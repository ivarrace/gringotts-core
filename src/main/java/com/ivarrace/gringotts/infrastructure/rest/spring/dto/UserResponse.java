package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserResponse {

  private UUID id;
  private LocalDateTime createdDate;
  private LocalDateTime lastModified;
  private String username;
  private List<String> authorities;

}
