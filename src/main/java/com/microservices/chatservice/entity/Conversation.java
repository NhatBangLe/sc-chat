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

    @Column(nullable = false)
    @Builder.Default
    private Boolean isRead = false;

    @Column(length = 100)
    private String title;

    @Column(nullable = false)
    private ConversationType type;

    @Column(nullable = false)
    @Builder.Default
    private Long messageCount = 0L;

    @Column(nullable = false)
    @Builder.Default
    private Integer participantCount = 0;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "creator_id", nullable = false, updatable = false, referencedColumnName = "id")
    private User creator;

    @OneToMany(mappedBy = "conversation", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Message> messages;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "conversation")
    private List<Participant> participants;

    @OneToMany(mappedBy = "conversation", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Attachment> attachments;
}
