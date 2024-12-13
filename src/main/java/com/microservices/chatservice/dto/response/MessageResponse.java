package com.microservices.chatservice.dto.response;

import com.microservices.chatservice.constant.MessageType;
import com.microservices.chatservice.entity.Message;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Message}
 */
public record MessageResponse(
        Long id,
        MessageType type,
        String text,
        List<String> attachmentIds,
        Long createdAt,
        String senderId,
        Long conversationId
) implements Serializable {
}