package com.api.notice.util.audit;


import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseUserEntity extends BaseTimeEntity {

    @CreatedDate
    @Column(name = "create_user")
    private String createUser;

    @LastModifiedDate
    @Column(name = "update_user")
    private String updateUser;

    @PrePersist
    public void onPrePersist() {
        createUser = "관리자등록";
        updateUser = "관리자등록";
    }

    @PreUpdate
    public void onPreUpdate() {
        updateUser = "관리자변경";
    }
}
