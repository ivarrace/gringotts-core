package com.ivarrace.gringotts.infrastructure.db.springdata.dbo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "groups")
@Getter
@Setter
public class GroupEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    private String name;
    private String type;
    @Column(nullable = false)
    private String key;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accountancy_id", nullable = false)
    private AccountancyEntity accountancy;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<CategoryEntity> categories;

}
