package com.microservices.chatservice.mapper;

import com.microservices.chatservice.dto.response.ParticipantResponse;
import com.microservices.chatservice.entity.Participant;

public class ParticipantMapper implements EntityMapper<Participant, ParticipantResponse> {

    @Override
    public ParticipantResponse toResponse(Participant entity) {
        return new ParticipantResponse(
                entity.getId(),
                entity.getType(),
                entity.getCreatedAt().getTime(),
                entity.getConversation().getId(),
                entity.getUser().getId()
        );
    }

}
