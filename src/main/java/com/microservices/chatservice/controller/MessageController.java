package com.microservices.chatservice.controller;

import com.microservices.chatservice.dto.response.MessageResponse;
import com.microservices.chatservice.service.message.IMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/${API_VERSION}/chat")
@Tag(
        name = "Message Controller",
        description = "All endpoints about messages of a conversation belongs an user."
)
@RequiredArgsConstructor
public class MessageController {

    private final IMessageService messageService;

    @GetMapping(path = "/{conversationId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Get all messages of a conversation.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid page number, page size.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conversation ID is not available.",
                    content = @Content
            )
    })
    public List<MessageResponse> getAllMessages(
            @PathVariable Long conversationId,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "6") Integer pageSize
    ) {
        return messageService.getAllMessages(conversationId, pageNumber, pageSize);
    }

    @DeleteMapping(path = "/{messageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Delete an existed text by a specific text ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Message deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Message ID is not available.")
    })
    public void deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
    }

}
