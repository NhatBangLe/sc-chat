package com.microservices.chatservice.repository;

import com.microservices.chatservice.entity.Conversation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query(
            name = "findAllConversationsByUserId",
            value = """
                    select distinct c from Conversation c
                    where c.id in (
                        select c.id from Conversation c
                        where c.creator.id = ?1
                        union
                        select c.id from Conversation c
                        join Participant p on c.id = p.conversation.id
                        where p.user.id = ?1
                    )"""
    )
    List<Conversation> findAllConversationsByUserId(String userId, Pageable pageable);
}
