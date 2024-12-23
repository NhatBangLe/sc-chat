package com.microservices.chatservice.service.message;

import com.microservices.chatservice.dto.response.MessageResponse;
import com.microservices.chatservice.dto.response.PagingObjectsResponse;
import com.microservices.chatservice.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl extends AbstractMessageService {

    private final MessageRepository messageRepository;

    @Override
    public PagingObjectsResponse<MessageResponse> getAllMessages(Long conversationId, Integer pageNumber, Integer pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        var messages = messageRepository.findAllByConversation_Id(conversationId, pageable);
        return new PagingObjectsResponse<>(
                messages.getTotalPages(),
                messages.getTotalElements(),
                messages.getNumber(),
                messages.getSize(),
                messages.getNumberOfElements(),
                messages.isFirst(),
                messages.isLast(),
                messages.map(this::mapToResponse).toList()
        );
    }

    @Override
    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

}
