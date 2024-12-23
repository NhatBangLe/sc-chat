package com.microservices.chatservice.repository;

import com.microservices.chatservice.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query(
            name = "findAllConversationsByUserId",
            value = """
                    select distinct c from Conversation c
                    where c.id in (
                        select c.id from Conversation c
                        where c.creator.id = :userId
                        union
                        select c.id from Conversation c
                        join Participant p on c.id = p.conversation.id
                        where p.user.id = :userId
                    )"""
    )
    Page<Conversation> findAllConversationsByUserId(@Param("userId") String userId, Pageable pageable);
}
