package com.microservices.chatservice.dto.request;

import com.microservices.chatservice.constant.ParticipantType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record ParticipantCreateRequest(
        @NotBlank(message = "User ID cannot be null/blank.")
        String userId,
        @NotNull(message = "Participant type cannot be null.")
        ParticipantType type,
        @NotNull(message = "Conversation ID cannot be null.")
        Long conversationId
) implements Serializable {
}
