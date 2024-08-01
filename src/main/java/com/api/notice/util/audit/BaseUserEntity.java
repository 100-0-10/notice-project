package com.api.notice.util.audit;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
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

}
