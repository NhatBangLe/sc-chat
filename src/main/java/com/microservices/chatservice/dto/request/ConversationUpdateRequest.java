package com.microservices.chatservice.dto.request;

import com.microservices.chatservice.entity.Conversation;

import java.io.Serializable;

/**
 * DTO for {@link Conversation}
 */
public record ConversationUpdateRequest(
        String title
) implements Serializable {
}