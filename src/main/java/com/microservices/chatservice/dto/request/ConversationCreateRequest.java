package com.microservices.chatservice.dto.request;

import com.microservices.chatservice.constant.ConversationType;
import com.microservices.chatservice.entity.Conversation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Conversation}
 */
public record ConversationCreateRequest(
        @Size(max = 100, message = "Title length cannot be greater than 100 characters")
        @NotBlank(message = "Title cannot be null/blank.")
        String title,
        @NotNull(message = "Conversation type cannot be null.")
        ConversationType type,
        @Size(min = 36, max = 36, message = "creatorId length must be 36 characters")
        @NotBlank(message = "Creator ID cannot be null/blank.")
        String creatorId
) implements Serializable {
}