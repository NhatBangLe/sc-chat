package com.microservices.chatservice.controller;

import com.microservices.chatservice.config.ChatServerConfigurationProperty;
import com.microservices.chatservice.dto.response.MessageResponse;
import com.microservices.chatservice.dto.request.ChatSendingMessage;
import com.microservices.chatservice.exception.WebSocketException;
import com.microservices.chatservice.service.websocket.WebSocketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/api/${API_VERSION}/chat")
@Tag(name = "WebSocket")
public class WebSocketController {

    private final ChatServerConfigurationProperty property;
    private final WebSocketService webSocketService;

    @GetMapping("/server")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Get chat server information.")
    public ChatServerConfigurationProperty getWebSocketServerInformation() {
        return property;
    }

    @MessageMapping("/conversation/{conversationId}")
    @SendTo("/topic/conversation/{conversationId}")
    public MessageResponse transferMessage(@DestinationVariable Long conversationId,
                                           @Payload ChatSendingMessage message) {
        return webSocketService.transferMessage(conversationId, message);
    }

    @MessageMapping("/user/{userId}")
    public void handleUserConnection(@DestinationVariable String userId,
                                @Payload boolean isConnected) {
        webSocketService.handleUserConnection(userId, isConnected);
    }

    @MessageExceptionHandler(WebSocketException.class)
    public void handleMessageException(WebSocketException e) {
        log.error("WebSocketController", e);
    }

}
