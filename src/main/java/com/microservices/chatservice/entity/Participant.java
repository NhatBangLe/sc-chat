package com.microservices.chatservice.entity;

import com.microservices.chatservice.constant.ParticipantType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "PARTICIPANT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private ParticipantType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversation_id", nullable = false, updatable = false, referencedColumnName = "id")
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false, updatable = false, referencedColumnName = "id")
    private User user;
}
