package com.microservices.chatservice.mapper;

import com.microservices.chatservice.dto.response.ConversationResponse;
import com.microservices.chatservice.entity.Conversation;

public class ConversationMapper implements EntityMapper<Conversation, ConversationResponse> {

    @Override
    public ConversationResponse toResponse(Conversation entity) {
        return new ConversationResponse(
                entity.getId(),
                entity.getIsRead(),
                entity.getTitle(),
                entity.getMessageCount(),
                entity.getParticipantCount(),
                entity.getCreatedAt().getTime(),
                entity.getUpdatedAt().getTime(),
                entity.getCreator().getId()
        );
    }

}
