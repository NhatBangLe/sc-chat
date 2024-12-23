package com.microservices.chatservice.service.attachment;

import com.microservices.chatservice.dto.response.AttachmentResponse;
import com.microservices.chatservice.dto.response.PagingObjectsResponse;
import com.microservices.chatservice.exception.NoEntityFoundException;
import com.microservices.chatservice.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl extends AbstractAttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Override
    public PagingObjectsResponse<AttachmentResponse> getAllAttachments(Long conversationId, Integer pageNumber, Integer pageSize)
            throws NoEntityFoundException {
        var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        var attachments = attachmentRepository.findAllByConversation_Id(conversationId, pageRequest);
        return new PagingObjectsResponse<>(
                attachments.getTotalPages(),
                attachments.getTotalElements(),
                attachments.getNumber(),
                attachments.getSize(),
                attachments.getNumberOfElements(),
                attachments.isFirst(),
                attachments.isLast(),
                attachments.map(this::mapToResponse).toList()
        );
    }

}
