package com.microservices.chatservice.service.conversation;

import com.microservices.chatservice.dto.request.ConversationCreateRequest;
import com.microservices.chatservice.dto.request.ConversationUpdateRequest;
import com.microservices.chatservice.dto.response.ConversationResponse;
import com.microservices.chatservice.dto.response.PagingObjectsResponse;
import com.microservices.chatservice.exception.NoEntityFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IConversationService {

    PagingObjectsResponse<ConversationResponse> getAllConversations(
            @Size(min = 36, max = 36, message = "userId length must be 36 characters.")
            @NotBlank(message = "User ID cannot be null/blank when getting all conversations.")
            String userId,
            @Min(value = 0, message = "Invalid page number (must positive) when getting all conversations.")
            @NotNull(message = "Page number cannot be null when getting all conversations.")
            Integer pageNumber,
            @Min(value = 1, message = "Invalid page size (must greater than 0) when getting all conversations.")
            @NotNull(message = "Page size cannot be null when getting all conversations.")
            Integer pageSize
    );

    ConversationResponse getConversation(
            @Min(value = 0, message = "conversationId cannot be negative.")
            @NotNull(message = "Conversation ID cannot be null/blank when getting a conversation.")
            Long conversationId
    ) throws NoEntityFoundException;

    /**
     * Creating a new conversation if not having an existed conversation between two users provided.
     * Otherwise, an ID of the existed conversation will be returned.
     *
     * @param conversationCreateRequest DTO using to create a new conversation.
     * @return The ID of a conversation.
     */
    Long createConversation(
            @Valid
            @NotNull(message = "Creating conversation data cannot be null.")
            ConversationCreateRequest conversationCreateRequest
    );

    void updateConversation(
            @Min(value = 0, message = "conversationId cannot be negative.")
            @NotNull(message = "Conversation ID cannot be null when updating a conversation.")
            Long conversationId,
            @NotNull(message = "Updating conversation data cannot be null.")
            ConversationUpdateRequest conversationUpdateRequest
    );

    void deleteConversation(
            @Min(value = 0, message = "conversationId cannot be negative.")
            @NotNull(message = "Conversation ID cannot be null when deleting a conversation.")
            Long conversationId
    );

}
