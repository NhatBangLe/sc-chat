package com.microservices.chatservice.dto.request;

import com.microservices.chatservice.constant.ConversationType;
import com.microservices.chatservice.entity.Conversation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link Conversation}
 */
public record ConversationCreateRequest(
        @NotBlank(message = "Conversation name cannot be null/blank.")
        String title,
        @NotNull(message = "Conversation type cannot be null.")
        ConversationType type,
        @NotBlank(message = "Creator ID cannot be null/blank.")
        String creatorId
) implements Serializable {
}