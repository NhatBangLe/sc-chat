package com.microservices.chatservice.controller;

import com.microservices.chatservice.dto.request.ConversationCreateRequest;
import com.microservices.chatservice.dto.request.ConversationUpdateRequest;
import com.microservices.chatservice.dto.response.ConversationResponse;
import com.microservices.chatservice.dto.response.PagingObjectsResponse;
import com.microservices.chatservice.service.conversation.IConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/${API_VERSION}/chat/conversation")
@Tag(
        name = "Conversation Controller",
        description = "All endpoints about conversations belongs an user."
)
@RequiredArgsConstructor
public class ConversationController {

    private final IConversationService conversationService;

    @GetMapping(path = "/{userId}/user")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Get all conversations by user having the userId. " +
                             "The result will be sorted by updatedAt descending.")
    @ApiResponse(
            responseCode = "400",
            description = "Invalid page number or page size.",
            content = @Content
    )
    public PagingObjectsResponse<ConversationResponse> getAllConversations(
            @PathVariable String userId,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "6") Integer pageSize
    ) {
        return conversationService.getAllConversations(userId, pageNumber, pageSize);
    }

    @GetMapping(path = "/{conversationId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Get an existed conversation by a specific conversation ID.")
    @ApiResponse(responseCode = "404", description = "Conversation ID is not available.", content = @Content)
    public ConversationResponse getConversation(@PathVariable Long conversationId) {
        return conversationService.getConversation(conversationId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create a new conversation is owned by a user having the creatorId.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Conversation created successfully. Response: The ID of the created conversation."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Conversation title or creator ID or type is null.",
                    content = @Content
            )
    })
    public Long createConversation(@RequestBody ConversationCreateRequest conversationCreateRequest) {
        return conversationService.createConversation(conversationCreateRequest);
    }

    @PatchMapping(path = "/{conversationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Update an existed conversation by a specific conversation ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Conversation title is blank."),
            @ApiResponse(responseCode = "404", description = "Conversation ID is not available.")
    })
    public void updateConversation(@PathVariable Long conversationId,
                                   @RequestBody ConversationUpdateRequest conversationUpdateRequest) {
        conversationService.updateConversation(conversationId, conversationUpdateRequest);
    }

    @DeleteMapping(path = "/{conversationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Delete an existed conversation by a specific conversation ID.")
    public void deleteConversation(@PathVariable Long conversationId) {
        conversationService.deleteConversation(conversationId);
    }

}
