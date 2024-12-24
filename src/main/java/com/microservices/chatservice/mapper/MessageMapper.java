package com.microservices.chatservice.mapper;

import com.microservices.chatservice.dto.response.MessageResponse;
import com.microservices.chatservice.entity.Attachment;
import com.microservices.chatservice.entity.Message;

import java.util.ArrayList;
import java.util.Objects;

public class MessageMapper implements EntityMapper<Message, MessageResponse> {

    @Override
    public MessageResponse toResponse(Message entity) {
        var attachmentIds = Objects
                .requireNonNullElse(entity.getAttachments(), new ArrayList<Attachment>())
                .stream()
                .map(Attachment::getId)
                .toList();

        return new MessageResponse(
                entity.getId(),
                entity.getType(),
                entity.getText(),
                attachmentIds,
                entity.getCreatedAt().getTime(),
                entity.getSender().getId(),
                entity.getConversation().getId()
        );
    }

}
