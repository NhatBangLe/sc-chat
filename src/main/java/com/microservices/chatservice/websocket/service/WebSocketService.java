package com.microservices.chatservice.websocket.service;

import com.microservices.chatservice.constant.MessageType;
import com.microservices.chatservice.constant.UserStatus;
import com.microservices.chatservice.dto.response.MessageResponse;
import com.microservices.chatservice.dto.response.NotificationResponse;
import com.microservices.chatservice.entity.*;
import com.microservices.chatservice.repository.UserRepository;
import com.microservices.chatservice.dto.request.ClientMessage;
import com.microservices.chatservice.exception.NoEntityFoundException;
import com.microservices.chatservice.repository.ConversationRepository;
import com.microservices.chatservice.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate template;

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public MessageResponse handleIncomingMessage(Long conversationId, ClientMessage clientMessage)
            throws NoEntityFoundException {
        var senderId = clientMessage.senderId();
        var text = clientMessage.text();
        var clientAttachments = clientMessage.attachments();

        var conversation = findConversation(conversationId);
        var sender = findUser(senderId);
        var newMessageBuilder = Message.builder()
                .type(MessageType.TEXT)
                .text(text)
                .sender(sender)
                .conversation(conversation);

        if (clientAttachments != null && !clientAttachments.isEmpty()) {
            var attachments = clientAttachments.stream()
                    .map(clientAttachment -> Attachment.builder()
                            .id(clientAttachment.id())
                            .fileUrl(clientAttachment.fileUrl())
                            .build())
                    .toList();
            newMessageBuilder.attachments(attachments);
            newMessageBuilder.type(text != null ? MessageType.BOTH : MessageType.FILE);
        }
        var newMessage = messageRepository.save(newMessageBuilder.build());

        // Notify to all participants of the conversation
        var userIds = conversation.getParticipants().stream()
                .map(Participant::getUser)
                .map(User::getId)
                .toList();
        notifyToUser(userIds, new NotificationResponse(conversationId));

        // Update message size for conversation
        conversation.setMessageCount(conversation.getMessageCount() + 1);
        conversationRepository.save(conversation);

        return new MessageResponse(
                newMessage.getId(),
                newMessage.getType(),
                text,
                newMessage.getCreatedAt().getTime(),
                senderId,
                conversationId
        );
    }

    /**
     * Send a notification message to "/topic/user/${userId}" destination with a payload.
     * @param userId Recipient ID
     * @param data A payload is transfer with the message.
     */
    public void notifyToUser(String userId, NotificationResponse data) {
        template.convertAndSend("/topic/user/" + userId, data);
    }

    public void notifyToUser(List<String> userIds, NotificationResponse data) {
        userIds.forEach(userId -> notifyToUser(userId, data));
    }

    public void handleUserHasConnected(String userId) {
        var user = userRepository.findById(userId)
                .orElse(User.builder()
                        .id(userId)
                        .build()
                );
        user.setStatus(UserStatus.ONLINE);
        userRepository.save(user);
    }

    public void handleUserHasDisconnected(String userId) {
        var user = findUser(userId);
        user.setStatus(UserStatus.OFFLINE);
        userRepository.save(user);
    }

    private Conversation findConversation(Long conversationId) throws NoEntityFoundException {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NoEntityFoundException("No conversation found with id: " + conversationId));
    }

    private User findUser(String userId) throws NoEntityFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoEntityFoundException("No user found with id: " + userId));
    }

}
