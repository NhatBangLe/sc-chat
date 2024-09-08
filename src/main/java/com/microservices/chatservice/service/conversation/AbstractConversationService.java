package com.microservices.chatservice.service.conversation;

import com.microservices.chatservice.dto.response.ConversationResponse;
import com.microservices.chatservice.entity.Conversation;

public abstract class AbstractConversationService implements IConversationService {

    protected ConversationResponse mapToResponse(final Conversation conversation) {
        return new ConversationResponse(
                conversation.getId(),
                conversation.getTitle(),
                conversation.getMessageCount(),
                conversation.getParticipantCount(),
                conversation.getCreatedAt().getTime(),
                conversation.getUpdatedAt().getTime(),
                conversation.getCreator().getId()
        );
    }

}
