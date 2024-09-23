package com.microservices.chatservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ATTACHMENT")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment extends AuditingEntity {
    @Id
    @Column(length = 36)
    private String id;

    @Column(length = 80)
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "message_id", nullable = false, updatable = false, referencedColumnName = "id")
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "conversation_id", nullable = false, updatable = false, referencedColumnName = "id")
    private Conversation conversation;
}
