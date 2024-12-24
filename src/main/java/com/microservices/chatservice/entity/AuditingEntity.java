package com.microservices.chatservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;

@Getter
@MappedSuperclass
public class AuditingEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column
    private Timestamp updatedAt;

}
