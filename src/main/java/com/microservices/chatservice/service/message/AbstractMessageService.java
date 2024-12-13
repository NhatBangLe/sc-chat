package com.microservices.chatservice.service.message;

import com.microservices.chatservice.dto.response.MessageResponse;
import com.microservices.chatservice.entity.Attachment;
import com.microservices.chatservice.entity.Message;

public abstract class AbstractMessageService implements IMessageService {

    protected MessageResponse mapToResponse(final Message message) {
        var attachmentIds = message.getAttachments().stream()
                .map(Attachment::getId)
                .toList();
        return new MessageResponse(
                message.getId(),
                message.getType(),
                message.getText(),
                attachmentIds,
                message.getCreatedAt().getTime(),
                message.getSender().getId(),
                message.getConversation().getId()
        );
    }

}
