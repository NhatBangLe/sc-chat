package com.microservices.chatservice.repository;

import com.microservices.chatservice.entity.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, String> {
  Page<Attachment> findAllByConversation_Id(Long id, Pageable pageable);
}