package com.microservices.chatservice.repository;

import com.microservices.chatservice.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByConversation_Id(@NonNull Long conversationId, Pageable pageable);
}