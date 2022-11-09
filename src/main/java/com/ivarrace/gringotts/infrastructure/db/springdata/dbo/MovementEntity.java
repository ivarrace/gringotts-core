package com.ivarrace.gringotts.infrastructure.db.springdata.dbo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
@Getter
@Setter
public class MovementEntity extends BaseEntity {

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double amount;

    private String info;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)

    @PreUpdate
    public void preUpdateFunction(){
        category.setLastModified(LocalDateTime.now());
        category.getGroup().setLastModified(LocalDateTime.now());
        category.getGroup().getAccountancy().setLastModified(LocalDateTime.now());
    }
}
