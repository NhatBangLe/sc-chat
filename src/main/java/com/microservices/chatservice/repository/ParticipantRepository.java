package com.microservices.chatservice.repository;

import com.microservices.chatservice.entity.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Page<Participant> findAllByConversation_Id(Long id, Pageable pageable);
    Optional<Participant> findByConversation_IdAndUser_Id(Long conversationId, String userId);
    boolean existsByConversation_IdAndUser_Id(Long conversationId, String userId);

}