package com.microservices.chatservice.dto.response;

import java.io.Serializable;

public record NotificationResponse(
        Long updatedConversationId
) implements Serializable {
}
