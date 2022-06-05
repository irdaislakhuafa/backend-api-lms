package com.capstoneprojectb12.lms.backendapilms.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.PreDestroy;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Slf4j
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @PrePersist
    public void onInsert() {
        var now = LocalDateTime.now();
        var me = this.getCurrentUser();

        this.createdAt = now;
        this.createdBy = me;
        this.updatedAt = null;
        this.updatedBy = null;
        this.isDeleted = false;
    }

    @PreUpdate
    public void onUpdate() {
        var now = LocalDateTime.now();
        var me = this.getCurrentUser();

        this.updatedAt = now;
        this.updatedBy = me;
        this.isDeleted = false;
    }

    @PreDestroy
    public void onDelete() {
        var now = LocalDateTime.now();
        var me = this.getCurrentUser();

        this.updatedAt = now;
        this.updatedBy = me;
        this.isDeleted = true;
    }

    private String getCurrentUser() {
        try {
            var email = SecurityContextHolder.getContext().getAuthentication().getName();
            return email;
        } catch (Exception e) {
            log.error("Current user is SYSTEM");
            return "SYSTEM";
        }
    }
}
