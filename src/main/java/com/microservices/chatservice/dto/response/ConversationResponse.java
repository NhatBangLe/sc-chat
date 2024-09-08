package com.microservices.chatservice.dto.response;

import com.microservices.chatservice.entity.Conversation;

import java.io.Serializable;

/**
 * DTO for {@link Conversation}
 */
public record ConversationResponse(
        Long id,
        String title,
        Long messageCount,
        Integer participantCount,
        Long createdAt,
        Long updatedAt,
        String creatorId
) implements Serializable {
}