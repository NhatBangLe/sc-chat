package com.microservices.chatservice.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

public record ClientMessage(
        String text,
        List<ClientAttachment> attachments,
        @NotBlank(message = "Sender ID cannot be null when sending a new message.")
        String senderId
) implements Serializable {
}