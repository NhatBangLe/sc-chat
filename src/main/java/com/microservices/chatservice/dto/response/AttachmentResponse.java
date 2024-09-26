package com.microservices.chatservice.dto.response;

import java.io.Serializable;

/**
 * DTO for {@link com.microservices.chatservice.entity.Attachment}
 */
public record AttachmentResponse(
        String id,
        Long messageId,
        Long createdAt
) implements Serializable {
}