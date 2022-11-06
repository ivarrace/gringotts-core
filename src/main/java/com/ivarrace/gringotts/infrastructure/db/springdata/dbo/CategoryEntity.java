package com.ivarrace.gringotts.infrastructure.db.springdata.dbo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class CategoryEntity extends BaseEntityAuditable {

    private String name;

    @Column(nullable = false)
    private String key;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<MovementEntity> movements;

    @PreUpdate
    public void preUpdateFunction(){
        group.setLastModified(LocalDateTime.now());
        group.getAccountancy().setLastModified(LocalDateTime.now());
    }
}
