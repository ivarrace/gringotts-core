package com.ivarrace.gringotts.infrastructure.db.springdata.dbo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "groups")
@Getter
@Setter
public class GroupEntity extends BaseEntityAuditable {

    private String name;

    private String type;

    @Column(nullable = false)
    private String key;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accountancy_id", nullable = false)
    private AccountancyEntity accountancy;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<CategoryEntity> categories;

    @PreUpdate
    public void preUpdateFunction(){
        accountancy.setLastModified(LocalDateTime.now());
    }
}
