package com.microservices.chatservice.service.participant;

import com.microservices.chatservice.dto.response.ParticipantResponse;
import com.microservices.chatservice.entity.Participant;

public abstract class AbstractParticipantService implements IParticipantService {

    protected ParticipantResponse mapToResponse(final Participant participant) {
        return new ParticipantResponse(
                participant.getId(),
                participant.getType(),
                participant.getCreatedAt().getTime(),
                participant.getConversation().getId(),
                participant.getUser().getId()
        );
    }

}
