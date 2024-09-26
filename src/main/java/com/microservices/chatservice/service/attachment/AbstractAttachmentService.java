package com.microservices.chatservice.service.attachment;

import com.microservices.chatservice.dto.response.AttachmentResponse;
import com.microservices.chatservice.entity.Attachment;

public abstract class AbstractAttachmentService implements IAttachmentService {

    protected AttachmentResponse mapToResponse(final Attachment attachment) {
        return new AttachmentResponse(
                attachment.getId(),
                attachment.getMessage().getId(),
                attachment.getCreatedAt().getTime()
        );
    }

}
