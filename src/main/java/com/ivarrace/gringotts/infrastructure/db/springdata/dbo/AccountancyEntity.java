package com.ivarrace.gringotts.infrastructure.db.springdata.dbo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accountancy")
@Getter
@Setter
public class AccountancyEntity extends BaseEntityAuditable {

    private String name;

    @Column(nullable = false)
    private String key;

    @OneToMany(mappedBy = "accountancy")
    private List<GroupEntity> groups;

    @OneToMany(mappedBy = "accountancy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountancyUserEntity> users;

}
