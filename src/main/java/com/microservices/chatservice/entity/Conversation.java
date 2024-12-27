package com.microservices.chatservice.entity;

import com.microservices.chatservice.constant.ConversationType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CONVERSATION")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conversation extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isRead = false;

    @Column(length = 100)
    private String title;

    @Column(nullable = false)
    private ConversationType type;

    @Builder.Default
    @Column(nullable = false)
    private Long messageCount = 0L;

    @Builder.Default
    @Column(nullable = false)
    private Integer participantCount = 0;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "creator_id", nullable = false, updatable = false, referencedColumnName = "id")
    private User creator;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "last_message_id", referencedColumnName = "id")
    private Message lastMessage;

    @OneToMany(mappedBy = "conversation", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Message> messages;

    @OneToMany(mappedBy = "conversation", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Participant> participants;

    @OneToMany(mappedBy = "conversation", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Attachment> attachments;
}
