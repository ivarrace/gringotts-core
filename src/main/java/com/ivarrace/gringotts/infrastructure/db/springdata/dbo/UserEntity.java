package com.ivarrace.gringotts.infrastructure.db.springdata.dbo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity extends BaseEntityAuditable {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String authority;

    private boolean nonExpired = true;

    private boolean nonLocked = true;

    private boolean credentialNonExpired = true;

    private boolean enabled = true;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<AccountancyUserEntity> accountancyList;

}
