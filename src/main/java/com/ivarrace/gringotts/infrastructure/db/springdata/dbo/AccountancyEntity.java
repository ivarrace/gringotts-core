package com.ivarrace.gringotts.infrastructure.db.springdata.dbo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "accountancy")
@Getter
@Setter
public class AccountancyEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModified;
    private String name;
    @Column(nullable = false)
    private String key;

    @OneToMany(mappedBy = "accountancy")
    private List<GroupEntity> groups;

    @OneToMany(mappedBy = "accountancy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<AccountancyUserEntity> users;

}
