package com.microservices.chatservice.dto.response;

import com.microservices.chatservice.constant.ParticipantType;

import java.io.Serializable;

/**
 * DTO for {@link com.microservices.chatservice.entity.Participant}
 */
public record ParticipantResponse(
        Long id,
        ParticipantType type,
        Long createdAt,
        Long conversationId,
        String userId
) implements Serializable {
}