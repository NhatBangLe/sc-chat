package com.microservices.chatservice.entity;

import com.microservices.chatservice.constant.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "USER")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditingEntity {
    @Id
    @Column
    private String id;

    @Column(nullable = false)
    private UserStatus status;

    @OneToMany(mappedBy = "creator", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Conversation> ownConversations;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Participant> joinConversations;

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;
}
