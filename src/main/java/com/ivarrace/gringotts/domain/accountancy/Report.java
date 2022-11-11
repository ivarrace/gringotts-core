package com.ivarrace.gringotts.domain.accountancy;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Report {

    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
    private int movementsNumber;
}