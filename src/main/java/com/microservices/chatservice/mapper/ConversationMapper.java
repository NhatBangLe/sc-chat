package com.microservices.chatservice.mapper;

import com.microservices.chatservice.dto.response.ConversationResponse;
import com.microservices.chatservice.entity.Conversation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConversationMapper implements EntityMapper<Conversation, ConversationResponse> {

    private final MessageMapper messageMapper;

    @Override
    public ConversationResponse toResponse(Conversation entity) {
        var message = messageMapper.toResponse(entity.getLastMessage());

        return new ConversationResponse(
                entity.getId(),
                entity.getIsRead(),
                entity.getTitle(),
                entity.getMessageCount(),
                message,
                entity.getParticipantCount(),
                entity.getCreatedAt().getTime(),
                entity.getUpdatedAt().getTime(),
                entity.getCreator().getId()
        );
    }

}
