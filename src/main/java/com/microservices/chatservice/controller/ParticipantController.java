package com.microservices.chatservice.controller;

import com.microservices.chatservice.dto.request.ParticipantCreateRequest;
import com.microservices.chatservice.dto.request.ParticipantUpdateRequest;
import com.microservices.chatservice.dto.response.PagingObjectsResponse;
import com.microservices.chatservice.dto.response.ParticipantResponse;
import com.microservices.chatservice.service.participant.IParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/${API_VERSION}/chat/participant")
@Tag(
        name = "Participant Controller",
        description = "All endpoints about participant belongs a conversation."
)
@RequiredArgsConstructor
public class ParticipantController {

    private final IParticipantService participantService;

    @GetMapping(path = "/{conversationId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Get all participants from the conversation.")
    @ApiResponse(
            responseCode = "400",
            description = "Invalid page number or page size.",
            content = @Content
    )
    public PagingObjectsResponse<ParticipantResponse> getAllParticipants(
            @PathVariable Long conversationId,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "6") Integer pageSize
    ) {
        return participantService.getAllParticipants(conversationId, pageNumber, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Register a new participant for the conversation.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Participant registered successfully. Response: The ID of the registered participant."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Conversation ID or participant type or user ID is null/invalid.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conversation ID is not available.",
                    content = @Content
            )
    })
    public Long addParticipant(@RequestBody ParticipantCreateRequest participantCreateRequest) {
        return participantService.addParticipant(participantCreateRequest);
    }

    @PatchMapping("{participantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Update an existing participant of conversation by participant ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Participant type is null/invalid."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Participant ID is not available."
            )
    })
    public void updateParticipant(
            @PathVariable Long participantId,
            @RequestBody ParticipantUpdateRequest participantUpdateRequest
    ) {
        participantService.updateParticipant(participantId, participantUpdateRequest);
    }

    @PatchMapping("{conversationId}/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Update an existing participant of conversation by user ID and conversation ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Participant type is null/invalid."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The user does not participate in conversation."
            )
    })
    public void updateParticipant(
            @PathVariable Long conversationId,
            @PathVariable String userId,
            @RequestBody ParticipantUpdateRequest participantUpdateRequest
    ) {
        participantService.updateParticipant(conversationId, userId, participantUpdateRequest);
    }

    @DeleteMapping("{participantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Delete an existing participant of conversation by participant ID.")
    public void deleteParticipant(@PathVariable Long participantId) {
        participantService.deleteParticipant(participantId);
    }

    @DeleteMapping("{conversationId}/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(
            responseCode = "404",
            description = "The user does not participate in conversation."
    )
    @Operation(description = "Delete an existing participant of conversation by user ID and conversation ID.")
    public void deleteParticipant(@PathVariable Long conversationId, @PathVariable String userId) {
        participantService.deleteParticipant(conversationId, userId);
    }

}
