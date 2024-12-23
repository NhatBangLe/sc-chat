package com.microservices.chatservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("chat")
public final class ChatServerConfigurationProperty {

    /**
     * Specify server host (or IP address) for establishing a STOMP WebSocket connection.
     */
    private String serverHost = "localhost";

    /**
     * Specify server port for establishing a STOMP WebSocket connection.
     */
    private int serverPort = 8080;

    /**
     * Specify a destination for publishing a new chat message.
     */
    private final String publishUserConnectionDest = "/sc/user/{userId}";

    /**
     * Specify a destination for publishing a new chat message.
     */
    private final String publishConversationDest = "/sc/conversation/{conversationId}";

    /**
     * Specify a destination for subscribing to the chat message broker.
     */
    private final String subscribeConversationDest = "/topic/conversation/{conversationId}";

    /**
     * Specify a destination for subscribing to the chat notification broker.
     */
    private final String subscribeNotificationDest = "/topic/notification/{userId}";

}