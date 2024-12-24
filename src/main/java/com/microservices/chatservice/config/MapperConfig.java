package com.microservices.chatservice.config;

import com.microservices.chatservice.mapper.AttachmentMapper;
import com.microservices.chatservice.mapper.ConversationMapper;
import com.microservices.chatservice.mapper.MessageMapper;
import com.microservices.chatservice.mapper.ParticipantMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ConversationMapper conversationMapper(MessageMapper messageMapper) {
        return new ConversationMapper(messageMapper);
    }

    @Bean
    public AttachmentMapper attachmentMapper() {
        return new AttachmentMapper();
    }

    @Bean
    public MessageMapper messageMapper() {
        return new MessageMapper();
    }

    @Bean
    public ParticipantMapper participantMapper() {
        return new ParticipantMapper();
    }

}
