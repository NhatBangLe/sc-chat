package com.microservices.chatservice.config;

import com.microservices.chatservice.service.websocket.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final WebSocketService service;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        var userId = getUserIdFromHeader(event.getMessage());
        if (userId != null) service.handleUserHasConnected(userId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        var userId = getUserIdFromHeader(event.getMessage());
        if (userId != null) service.handleUserHasDisconnected(userId);
    }

    private String getUserIdFromHeader(Message<?> message) {
        var headerAccessor = StompHeaderAccessor.wrap(message);
        return headerAccessor.getFirstNativeHeader("userId");
    }

}
