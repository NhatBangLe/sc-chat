package com.microservices.chatservice.service.participant;

import com.microservices.chatservice.constant.UserStatus;
import com.microservices.chatservice.dto.request.ParticipantCreateRequest;
import com.microservices.chatservice.dto.request.ParticipantUpdateRequest;
import com.microservices.chatservice.dto.response.PagingObjectsResponse;
import com.microservices.chatservice.dto.response.ParticipantResponse;
import com.microservices.chatservice.entity.Conversation;
import com.microservices.chatservice.entity.Participant;
import com.microservices.chatservice.entity.User;
import com.microservices.chatservice.exception.NoEntityFoundException;
import com.microservices.chatservice.mapper.ParticipantMapper;
import com.microservices.chatservice.repository.ConversationRepository;
import com.microservices.chatservice.repository.ParticipantRepository;
import com.microservices.chatservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements IParticipantService {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final ParticipantRepository participantRepository;

    private final ParticipantMapper mapper;

    @Override
    public PagingObjectsResponse<ParticipantResponse> getAllParticipants(Long conversationId, Integer pageNumber, Integer pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        var participants = participantRepository.findAllByConversation_Id(conversationId, pageable);
        return new PagingObjectsResponse<>(
                participants.getTotalPages(),
                participants.getTotalElements(),
                participants.getNumber(),
                participants.getSize(),
                participants.getNumberOfElements(),
                participants.isFirst(),
                participants.isLast(),
                participants.map(mapper::toResponse).toList()
        );
    }

    @Override
    public Long addParticipant(ParticipantCreateRequest participantCreateRequest) throws NoEntityFoundException {
        var userId = participantCreateRequest.userId();
        var user = userRepository.findById(userId)
                .orElse(User.builder()
                        .id(userId)
                        .status(UserStatus.OFFLINE)
                        .build()
                );
        var conversation = findConversation(participantCreateRequest.conversationId());
        conversation.setParticipantCount(conversation.getParticipantCount() + 1);

        var participant = Participant.builder()
                .type(participantCreateRequest.type())
                .conversation(conversation)
                .user(user)
                .build();
        return participantRepository.save(participant).getId();
    }

    @Override
    public void updateParticipant(Long participantId, ParticipantUpdateRequest participantUpdateRequest) {
        var participant = findParticipant(participantId);
        updateParticipant(participant, participantUpdateRequest);
    }

    @Override
    public void updateParticipant(Long conversationId, String userId, ParticipantUpdateRequest participantUpdateRequest) {
        var participant = findParticipant(conversationId, userId);
        updateParticipant(participant, participantUpdateRequest);
    }

    private void updateParticipant(Participant participant, ParticipantUpdateRequest participantUpdateRequest) {
        var isUpdated = false;

        var type = participantUpdateRequest.type();
        if (type != null) {
            participant.setType(type);
            isUpdated = true;
        }

        if (isUpdated) participantRepository.save(participant);
    }

    @Override
    public void deleteParticipant(Long participantId) {
        participantRepository.deleteById(participantId);
    }

    @Override
    public void deleteParticipant(Long conversationId, String userId) {
        var participant = findParticipant(conversationId, userId);
        participantRepository.delete(participant);
    }

    private Conversation findConversation(Long conversationId) throws NoEntityFoundException {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NoEntityFoundException("No conversation found with id: " + conversationId));
    }

    private Participant findParticipant(Long participantId) throws NoEntityFoundException {
        return participantRepository.findById(participantId)
                .orElseThrow(() -> new NoEntityFoundException("No participant found with id: " + participantId));
    }

    private Participant findParticipant(Long conversationId, String userId) throws NoEntityFoundException {
        return participantRepository.findByConversation_IdAndUser_Id(conversationId, userId)
                .orElseThrow(() -> new NoEntityFoundException("No participant found with conversation id: " +
                                                              conversationId + " and user id: " + userId));
    }

}
