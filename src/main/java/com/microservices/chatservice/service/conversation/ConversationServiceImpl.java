package com.microservices.chatservice.service.conversation;

import com.microservices.chatservice.constant.UserStatus;
import com.microservices.chatservice.dto.request.ConversationCreateRequest;
import com.microservices.chatservice.dto.request.ConversationUpdateRequest;
import com.microservices.chatservice.dto.response.ConversationResponse;
import com.microservices.chatservice.dto.response.PagingObjectsResponse;
import com.microservices.chatservice.entity.Conversation;
import com.microservices.chatservice.entity.User;
import com.microservices.chatservice.exception.IllegalAttributeException;
import com.microservices.chatservice.exception.NoEntityFoundException;
import com.microservices.chatservice.mapper.ConversationMapper;
import com.microservices.chatservice.repository.ConversationRepository;
import com.microservices.chatservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements IConversationService {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    private final ConversationMapper mapper;

    @Override
    public PagingObjectsResponse<ConversationResponse> getAllConversations(String userId, Integer pageNumber, Integer pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize, Sort.by("updatedAt").descending());
        var conversations = conversationRepository.findAllConversationsByUserId(userId, pageable);
        return new PagingObjectsResponse<>(
                conversations.getTotalPages(),
                conversations.getTotalElements(),
                conversations.getNumber(),
                conversations.getSize(),
                conversations.getNumberOfElements(),
                conversations.isFirst(),
                conversations.isLast(),
                conversations.map(mapper::toResponse).toList()
        );
    }

    @Override
    public ConversationResponse getConversation(Long conversationId) throws NoEntityFoundException {
        var conversation = findConversation(conversationId);
        return mapper.toResponse(conversation);
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
            if (title.isBlank() || title.length() > 100)
                throw new IllegalAttributeException("Conversation title cannot be blank or longer than 100 characters.");
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
