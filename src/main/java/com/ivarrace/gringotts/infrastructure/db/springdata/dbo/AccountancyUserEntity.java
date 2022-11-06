package com.ivarrace.gringotts.infrastructure.db.springdata.dbo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "accountancy_users")
@Getter
@Setter
public class AccountancyUserEntity extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "accountancy_id")
    private AccountancyEntity accountancy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String scope;
}
