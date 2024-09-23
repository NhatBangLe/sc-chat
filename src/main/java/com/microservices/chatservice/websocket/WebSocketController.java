package com.microservices.chatservice.websocket;

import com.microservices.chatservice.dto.response.MessageResponse;
import com.microservices.chatservice.dto.request.ClientMessage;
import com.microservices.chatservice.exception.WebSocketException;
import com.microservices.chatservice.websocket.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {

    private final WebSocketService webSocketService;

    @MessageMapping("/conversation/{conversationId}")
    @SendTo("/topic/conversation/{conversationId}")
    public MessageResponse transferMessage(@DestinationVariable Long conversationId,
                                           @Payload ClientMessage message) {
        return webSocketService.handleIncomingMessage(conversationId, message);
    }

    @MessageMapping("/user/{userId}/connected")
    public void handleUserHasConnected(@DestinationVariable String userId) {
        webSocketService.handleUserHasConnected(userId);
    }

    @MessageMapping("/user/{userId}/disconnected")
    public void handleUserHasDisconnected(@DestinationVariable String userId) {
        webSocketService.handleUserHasDisconnected(userId);
    }

    @MessageExceptionHandler(WebSocketException.class)
    public void handleMessageException(WebSocketException e) {
        log.error("", e);
    }

}
