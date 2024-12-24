package com.microservices.chatservice.dto.request;

import com.microservices.chatservice.constant.ParticipantType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record ParticipantCreateRequest(
        @Size(min = 36, max = 36, message = "userId length must be 36 characters.")
        @NotBlank(message = "User ID cannot be null/blank.")
        String userId,
        @NotNull(message = "Participant type cannot be null.")
        ParticipantType type,
        @Min(value = 0, message = "conversationId cannot be negative.")
        @NotNull(message = "Conversation ID cannot be null.")
        Long conversationId
) implements Serializable {
}
