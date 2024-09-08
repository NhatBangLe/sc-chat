package com.microservices.chatservice.repository;

import com.microservices.chatservice.entity.Attachment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, String> {
  List<Attachment> findAllByConversation_Id(Long id, Pageable pageable);
}