package com.microservices.chatservice.entity;

import com.microservices.chatservice.constant.MessageType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "MESSAGE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private MessageType type;

    @Column
    private String text;

    @OneToMany(mappedBy = "message", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Attachment> attachments;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sender_id", nullable = false, updatable = false, referencedColumnName = "id")
    private User sender;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "conversation_id", nullable = false, updatable = false, referencedColumnName = "id")
    private Conversation conversation;
}
