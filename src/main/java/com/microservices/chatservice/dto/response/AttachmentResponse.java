package com.microservices.chatservice.dto.response;

import java.io.Serializable;

/**
 * DTO for {@link com.microservices.chatservice.entity.Attachment}
 */
public record AttachmentResponse(
        String id,
        Long messageId,
        String fileUrl,
        Long createdAt
) implements Serializable {
}