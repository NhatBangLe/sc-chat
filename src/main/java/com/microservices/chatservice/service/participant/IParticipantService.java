package com.microservices.chatservice.service.participant;

import com.microservices.chatservice.dto.request.ParticipantCreateRequest;
import com.microservices.chatservice.dto.request.ParticipantUpdateRequest;
import com.microservices.chatservice.dto.response.PagingObjectsResponse;
import com.microservices.chatservice.dto.response.ParticipantResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IParticipantService {

    PagingObjectsResponse<ParticipantResponse> getAllParticipants(
            @NotNull(message = "Conversation ID cannot be null when getting all participants.")
            Long conversationId,
            @Min(value = 0, message = "Invalid page number (must positive) when getting all participants.")
            @NotNull(message = "Page number cannot be null when getting all participants.")
            Integer pageNumber,
            @Min(value = 1, message = "Invalid page size (must greater than 0) when getting all participants.")
            @NotNull(message = "Page size cannot be null when getting all participants.")
            Integer pageSize
    );

    Long addParticipant(
            @Valid
            @NotNull(message = "Creating participant data cannot be null.")
            ParticipantCreateRequest participantCreateRequest
    );

    void updateParticipant(
            @NotNull(message = "Participant ID cannot be null when deleting participant.")
            Long participantId,
            @NotNull(message = "Updating participant data cannot be null.")
            ParticipantUpdateRequest participantUpdateRequest
    );

    void updateParticipant(
            @NotNull(message = "Conversation ID cannot be null when updating participant.")
            Long conversationId,
            @NotBlank(message = "User ID cannot be null/blank when updating participant.")
            String userId,
            @NotNull(message = "Updating participant data cannot be null.")
            ParticipantUpdateRequest participantUpdateRequest
    );

    void deleteParticipant(
            @NotNull(message = "Participant ID cannot be null when deleting participant.")
            Long participantId
    );

    void deleteParticipant(
            @NotNull(message = "Conversation ID cannot be null when deleting participant.")
            Long conversationId,
            @NotBlank(message = "User ID cannot be null/blank when deleting participant.")
            String userId
    );

}
