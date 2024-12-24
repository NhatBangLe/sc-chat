package com.microservices.chatservice.mapper;

import com.microservices.chatservice.dto.response.AttachmentResponse;
import com.microservices.chatservice.entity.Attachment;

public class AttachmentMapper implements EntityMapper<Attachment, AttachmentResponse> {

    @Override
    public AttachmentResponse toResponse(Attachment entity) {
        return new AttachmentResponse(
                entity.getId(),
                entity.getMessage().getId(),
                entity.getCreatedAt().getTime()
        );
    }

}
