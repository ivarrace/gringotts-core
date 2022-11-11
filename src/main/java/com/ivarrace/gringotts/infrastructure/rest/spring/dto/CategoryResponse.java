package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryResponse {

    private String key;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;

}
