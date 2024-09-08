package com.microservices.chatservice.service.attachment;

import com.microservices.chatservice.dto.response.AttachmentResponse;
import com.microservices.chatservice.exception.NoEntityFoundException;
import com.microservices.chatservice.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl extends AbstractAttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Override
    public List<AttachmentResponse> getAllAttachments(Long conversationId, Integer pageNumber, Integer pageSize)
            throws NoEntityFoundException {
        var pageable = PageRequest.of(pageNumber, pageSize);
        return attachmentRepository.findAllByConversation_Id(conversationId, pageable).stream()
                .map(this::mapToResponse)
                .toList();
    }

}
