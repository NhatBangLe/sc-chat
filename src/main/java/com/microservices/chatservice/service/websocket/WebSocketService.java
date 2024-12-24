package com.microservices.chatservice.service.websocket;

import com.microservices.chatservice.constant.MessageType;
import com.microservices.chatservice.constant.UserStatus;
import com.microservices.chatservice.config.ChatServerConfigurationProperty;
import com.microservices.chatservice.dto.response.MessageResponse;
import com.microservices.chatservice.dto.response.NotificationResponse;
import com.microservices.chatservice.entity.*;
import com.microservices.chatservice.exception.WebSocketException;
import com.microservices.chatservice.mapper.MessageMapper;
import com.microservices.chatservice.repository.ParticipantRepository;
import com.microservices.chatservice.repository.UserRepository;
import com.microservices.chatservice.dto.request.ChatSendingMessage;
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

    private final ChatServerConfigurationProperty property;
    private final SimpMessagingTemplate template;

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final ParticipantRepository participantRepository;
    private final MessageRepository messageRepository;

    private final MessageMapper mapper;

    public MessageResponse transferMessage(Long conversationId, ChatSendingMessage chatSendingMessage)
            throws NoEntityFoundException {
        var senderId = chatSendingMessage.senderId();
        var text = chatSendingMessage.text();
        var attachmentIds = chatSendingMessage.attachmentIds();
        if (!participantRepository.existsByConversation_IdAndUser_Id(conversationId, senderId))
            throw new WebSocketException("Participant not found.");

        var conversation = findConversation(conversationId);
        var sender = findUser(senderId);

        var newMessage = Message.builder()
                .type(MessageType.TEXT)
                .text(text)
                .sender(sender)
                .conversation(conversation)
                .build();

        if (attachmentIds != null && !attachmentIds.isEmpty()) {
            var attachments = attachmentIds.stream()
                    .map(attachmentId -> Attachment.builder()
                            .id(attachmentId)
                            .conversation(conversation)
                            .message(newMessage)
                            .build())
                    .toList();
            newMessage.setAttachments(attachments);
            newMessage.setType(text != null ? MessageType.BOTH : MessageType.FILE);
        }
        var newMessageSaved = messageRepository.save(newMessage);

        // Notify to all participants of the conversation
        var userIds = conversation.getParticipants().stream()
                .map(Participant::getUser)
                .map(User::getId)
                .toList();
        notifyToUser(userIds, new NotificationResponse(conversationId));

        // Update message size for conversation
        conversation.setMessageCount(conversation.getMessageCount() + 1);
        conversation.setLastMessage(newMessage);
        conversationRepository.save(conversation);

        return mapper.toResponse(newMessageSaved);
    }

    /**
     * Send a notification message to "/topic/notification/${userId}" destination with a payload.
     *
     * @param userId Recipient ID
     * @param data   A payload is transfer with the message.
     */
    public void notifyToUser(String userId, NotificationResponse data) {
        var notificationDest = property.getSubscribeNotificationDest()
                .replace("{userId}", userId);
        template.convertAndSend(notificationDest, data);
    }

    public void notifyToUser(List<String> userIds, NotificationResponse data) {
        userIds.forEach(userId -> notifyToUser(userId, data));
    }

    public void handleUserConnection(String userId, Boolean isConnected) {
        User user;
        if (isConnected) {
            user = userRepository.findById(userId)
                    .orElse(User.builder().id(userId).build());
            user.setStatus(UserStatus.ONLINE);
        }
        else {
            user = findUser(userId);
            user.setStatus(UserStatus.OFFLINE);
        }
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
