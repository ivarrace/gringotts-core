package com.ivarrace.gringotts.infrastructure.db.springdata.dbo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "accountancy_users")
@Getter
@Setter
public class AccountancyUserEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "accountancy_id")
    AccountancyEntity accountancy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    String scope;
}
