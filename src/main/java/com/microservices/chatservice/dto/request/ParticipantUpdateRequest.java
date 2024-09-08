package com.microservices.chatservice.dto.request;

import com.microservices.chatservice.constant.ParticipantType;

import java.io.Serializable;

public record ParticipantUpdateRequest(
        ParticipantType type
) implements Serializable {
}
