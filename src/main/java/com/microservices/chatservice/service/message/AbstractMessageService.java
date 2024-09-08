package com.microservices.chatservice.service.message;

import com.microservices.chatservice.dto.response.MessageResponse;
import com.microservices.chatservice.entity.Message;

public abstract class AbstractMessageService implements IMessageService {

    protected MessageResponse mapToResponse(final Message message) {
        return new MessageResponse(
                message.getId(),
                message.getType(),
                message.getText(),
                message.getCreatedAt().getTime(),
                message.getSender().getId(),
                message.getConversation().getId()
        );
    }

}
