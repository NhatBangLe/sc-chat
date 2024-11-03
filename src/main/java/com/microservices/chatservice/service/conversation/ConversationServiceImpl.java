package com.microservices.chatservice.service.conversation;

import com.microservices.chatservice.constant.UserStatus;
import com.microservices.chatservice.dto.request.ConversationCreateRequest;
import com.microservices.chatservice.dto.request.ConversationUpdateRequest;
import com.microservices.chatservice.dto.response.ConversationResponse;
import com.microservices.chatservice.entity.Conversation;
import com.microservices.chatservice.entity.User;
import com.microservices.chatservice.exception.IllegalAttributeException;
import com.microservices.chatservice.exception.NoEntityFoundException;
import com.microservices.chatservice.repository.ConversationRepository;
import com.microservices.chatservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl extends AbstractConversationService {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    @Override
    public List<ConversationResponse> getAllConversations(String userId, Integer pageNumber, Integer pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize, Sort.by("updatedAt").descending());
        return conversationRepository.findAllConversationsByUserId(userId, pageable)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ConversationResponse getConversation(Long conversationId) throws NoEntityFoundException {
        var conversation = findConversation(conversationId);
        return mapToResponse(conversation);
    }

    @Override
    public Long createConversation(ConversationCreateRequest conversationCreateRequest)
            throws IllegalAttributeException {
        var creatorId = conversationCreateRequest.creatorId();
        var creator = userRepository.findById(creatorId)
                .orElse(User.builder()
                        .id(creatorId)
                        .status(UserStatus.ONLINE)
                        .build()
                );

        var conversation = Conversation.builder()
                .title(conversationCreateRequest.title())
                .type(conversationCreateRequest.type())
                .messageCount(0L)
                .participantCount(0)
                .creator(creator)
                .build();
        return conversationRepository.save(conversation).getId();
    }

    @Override
    public void updateConversation(Long conversationId, ConversationUpdateRequest conversationUpdateRequest)
            throws NoEntityFoundException {
        var isUpdated = false;
        var conversation = findConversation(conversationId);

        var title = conversationUpdateRequest.title();
        if (title != null) {
            if (title.isBlank())
                throw new IllegalAttributeException("Conversation title cannot be blank when updating a conversation.");
            conversation.setTitle(title);
            isUpdated = true;
        }

        if (isUpdated) conversationRepository.save(conversation);
    }

    @Override
    public void deleteConversation(Long conversationId) {
        conversationRepository.deleteById(conversationId);
    }

    private Conversation findConversation(Long conversationId) throws NoEntityFoundException {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NoEntityFoundException("No conversation found with id: " + conversationId));
    }

}
