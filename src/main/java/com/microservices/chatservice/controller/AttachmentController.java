package com.microservices.chatservice.controller;

import com.microservices.chatservice.dto.response.AttachmentResponse;
import com.microservices.chatservice.service.attachment.IAttachmentService;
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
@RequestMapping(path = "/api/${API_VERSION}/chat/attachment")
@Tag(
        name = "Attachment Controller",
        description = "All endpoints about attachments belongs a conversation of an user."
)
@RequiredArgsConstructor
public class AttachmentController {

    private final IAttachmentService attachmentService;

    @GetMapping(path = "/{conversationId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Get all attachments of a conversation.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid page number, page size.",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404", description = "Conversation ID is not available.", content = @Content)
    })
    public List<AttachmentResponse> getAllAttachments(
            @PathVariable Long conversationId,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "6") Integer pageSize
    ) {
        return attachmentService.getAllAttachments(conversationId, pageNumber, pageSize);
    }

}
