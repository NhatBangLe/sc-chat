package com.microservices.chatservice.service.attachment;

import com.microservices.chatservice.dto.response.AttachmentResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface IAttachmentService {

    List<AttachmentResponse> getAllAttachments(
            @NotNull(message = "Conversation ID cannot be null when getting all attachmentIds.")
            Long conversationId,
            @Min(value = 0, message = "Invalid page number (must positive) when getting all attachmentIds.")
            @NotNull(message = "Page number cannot be null when getting all attachmentIds.")
            Integer pageNumber,
            @Min(value = 1, message = "Invalid page size (must greater than 0) when getting all attachmentIds.")
            @NotNull(message = "Page size cannot be null when getting all attachmentIds.")
            Integer pageSize
    );

}
