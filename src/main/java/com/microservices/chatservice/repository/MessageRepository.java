package com.microservices.chatservice.repository;

import com.microservices.chatservice.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByConversation_Id(@NonNull Long conversationId, Pageable pageable);
}