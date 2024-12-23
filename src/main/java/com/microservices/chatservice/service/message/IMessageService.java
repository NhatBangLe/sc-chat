package com.microservices.chatservice.service.message;

import com.microservices.chatservice.dto.response.MessageResponse;
import com.microservices.chatservice.dto.response.PagingObjectsResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IMessageService {

    PagingObjectsResponse<MessageResponse> getAllMessages(
            @NotNull(message = "Conversation ID cannot be null when getting all messages.")
            Long conversationId,
            @Min(value = 0, message = "Invalid page number (must positive) when getting all messages.")
            @NotNull(message = "Page number cannot be null when getting all messages.")
            Integer pageNumber,
            @Min(value = 1, message = "Invalid page size (must greater than 0) when getting all messages.")
            @NotNull(message = "Page size cannot be null when getting all messages.")
            Integer pageSize
    );

    void deleteMessage(
            @NotNull(message = "Message ID cannot be null when deleting a text.")
            Long messageId
    );

}
