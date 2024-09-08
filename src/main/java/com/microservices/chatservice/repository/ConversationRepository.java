package com.microservices.chatservice.repository;

import com.microservices.chatservice.entity.Conversation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findAllByCreator_IdOrParticipants_User_Id(
            String creatorId, String userId, Pageable pageable);
}
